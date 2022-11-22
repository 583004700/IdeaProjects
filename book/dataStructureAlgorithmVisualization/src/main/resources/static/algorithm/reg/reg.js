// 字符的匹配规则,默认相等才匹配上
class CharMatch {
    constructor(patternChar) {
        this.patternChar = patternChar;
    }

    test(str) {
        return this.patternChar === str;
    }

    equals(other) {
        if (!other) {
            return false;
        }
        return this.patternChar === other.patternChar;
    }
}

// .字符的匹配规则，匹配任意一个字符
class DotCharMatch extends CharMatch {
    constructor(patternChar) {
        super(patternChar);
    }

    test(str) {
        if (str === undefined) {
            return false;
        }
        return true;
    }
}

// 数字匹配
class NumberCharMatch extends CharMatch {
    constructor(patternChar) {
        super(patternChar);
    }

    test(str) {
        return str.length === 1 && parseInt(str) >= 0 && parseInt(str) <= 9;
    }
}

class CharMatchFactory {
    static getCharMatch(patternChar) {
        let result = null;
        switch (patternChar) {
            case ".":
                result = new DotCharMatch(patternChar);
                break;
            case "$":
                result = new NumberCharMatch(patternChar);
                break;
            default:
                result = new CharMatch(patternChar);
                break;
        }
        return result;
    }
}

class StateNode {
    constructor(id = null, accept = false) {
        // 是否可接受
        this.accept = accept;
        this.charMatchs = {};
        this.pre = null;
        this.next = null;
        this.id = id;
        this.patternAndCount = null;
    }

    setPatternAndCount(patternAndCount) {
        this.patternAndCount = patternAndCount;
    }

    // 添加一个匹配规则及流向的结点,如 \d , a
    addCharMatch(patternChar, stateNode) {
        let key = patternChar;
        let charMatchArr = this.charMatchs[key];
        if (charMatchArr === undefined) {
            charMatchArr = [];
            this.charMatchs[key] = charMatchArr;
        }
        for (let i = 0; i < charMatchArr.length; i++) {
            // 相同的结点状态已经存在了，不需要再添加了
            if (charMatchArr[i] === stateNode) {
                return;
            }
        }
        charMatchArr.push(stateNode);
    }
}

// 有限状态机
class FSM {
    constructor() {
        this.header = null;
        this.tail = null;
        // 接受输入之后，当前在哪些点,里面放StateNode对象
        this.currentArr = [];
    }

    addStateNode(stateNode) {
        if (this.header === null) {
            this.header = stateNode;
            this.tail = stateNode;
            this.currentArr.push(this.header);
            return this;
        }
        this.tail.next = stateNode;
        stateNode.pre = this.tail;
        this.tail = stateNode;
        return this;
    }

    // 接受字符输入
    acceptInput(c) {
        let currentArr = this.currentArr;
        this.currentArr = [];
        let cache = {};
        for (let i = 0; i < currentArr.length; i++) {
            let current = currentArr[i];
            for (let key in current.charMatchs) {
                let charM = CharMatchFactory.getCharMatch(key);
                if (charM.test(c)) {
                    let newCurrentArr = current.charMatchs[key];
                    if (newCurrentArr !== undefined) {
                        for (let j = 0; j < newCurrentArr.length; j++) {
                            let key = newCurrentArr[j].id;
                            if (cache[key] === undefined) {
                                this.currentArr.push(newCurrentArr[j]);
                                cache[key] = 1;
                            }
                        }
                    }
                }
            }
        }
        return this.currentArr.length > 0;
    }

    // 判断是否到达可接受状态
    isAccept() {
        let currentArr = this.currentArr;
        for (let i = 0; i < currentArr.length; i++) {
            let current = currentArr[i];
            if (current.accept) {
                return true;
            }
        }
        return false;
    }
}

class ExecResult {

}

class PatternAndCount {
    // 匹配模式和重复次数,count为字符串
    constructor(singlePattern, count) {
        this.singlePattern = singlePattern;
        this.count = count;
    }

    getCount() {
        if (this.count !== "*" && this.count !== "?" && this.count !== "+") {
            return parseInt(this.count);
        }
        return this.count;
    }
}

class Reg {
    constructor(pattern, global = false) {
        this.global = global;
        // 从哪个坐标开始匹配，如果是全局匹配，每次匹配成功后，需要修改这个值
        this.startIndex = 0;
        this.fsm = null;
        this.parse(pattern);
        this.pattern = pattern;
    }

    _parsePatternCompleteChar(pattern) {
        // 返回值为 [{PatternAndCount}]

        // 字符匹配

        //  .  任意字符
        //  \\w 查找数字、字母及下划线。
        //  \\d 查找数字
        //  \\D 查找非数字
        //  \\s 查找空白字符
        //  \\S 查找非空白字符

        // 量词

        // *  匹配0次或多次
        // +  匹配1次或多次
        // ?  匹配0次或1次

        let primarySinglePatternPrefix = "\\";
        let primarySinglePatternPrefixes = "wdDsS";
        let primaryCountSuffixes = "*+?";
        let result = [];
        for (let i = 0; i < pattern.length; i++) {
            let singlePattern = null;
            let count = null;
            let c = pattern.substr(i, 1);
            if (c === primarySinglePatternPrefix) {
                continue;
            }
            if (i > 0 && pattern.substr(i - 1, 1) === primarySinglePatternPrefix) {
                if (primarySinglePatternPrefixes.indexOf(c) === -1) {
                    throw "正则表达式不正确，\\" + c + "不是正确的匹配模式！";
                }
                // 如果前面字符是 \\
                singlePattern = primarySinglePatternPrefix + c;
            } else {
                singlePattern = c;
            }
            let nextC = null;
            if (i < pattern.length - 1) {
                nextC = pattern.substr(i + 1, 1);
            }
            if (nextC && primaryCountSuffixes.indexOf(nextC) !== -1) {
                count = nextC;
                i++;
            } else {
                count = "1";
            }

            let patternAndCount = new PatternAndCount(singlePattern, count);
            result.push(patternAndCount);
        }
        return result;
    }

    parse(pattern) {
        if (pattern === this.pattern) {
            return;
        }
        this.fsm = new FSM();
        let count = 1;
        let first = new StateNode(count);
        this.fsm.addStateNode(first);
        let preNoStar = this.fsm.header;
        let stateNode = null;
        let prePre = null;

        let patternAndCountArr = this._parsePatternCompleteChar(pattern);

        for (let i = 0; i < patternAndCountArr.length; i++) {
            let patternAndCount = patternAndCountArr[i];
            let ch = patternAndCount.singlePattern;
            let haveStar = patternAndCount.getCount() === "*";
            let charReal = ch;
            stateNode = new StateNode(++count);
            stateNode.setPatternAndCount(patternAndCount);
            this.fsm.addStateNode(stateNode);
            stateNode.pre.addCharMatch(charReal, stateNode);
            prePre = preNoStar;
            if (haveStar) {
                stateNode.addCharMatch(charReal, stateNode);
            } else {
                preNoStar = stateNode;
            }
            while (prePre !== null && prePre !== stateNode) {
                prePre.addCharMatch(charReal, stateNode);
                prePre = prePre.next;
            }
        }

        // 如果结点最后是 * 号，代表重复0次也行，所以他前面的也是可接受状态。
        let acceptStateNode = this.fsm.tail;
        while (acceptStateNode !== null && acceptStateNode.patternAndCount
        && acceptStateNode.patternAndCount.count === "*") {
            acceptStateNode.accept = true;
            acceptStateNode = acceptStateNode.pre;
        }
        if (acceptStateNode && acceptStateNode !== this.fsm.header) {
            acceptStateNode.accept = true;
        }
    }

    test(str) {
        for (let i = 0; i < str.length; i++) {
            let c = str.substr(i, 1);
            let b = this.fsm.acceptInput(c);
            if (!b) {
                return false;
            }
        }
        return this.fsm.isAccept();
    }

    exec(str) {

    }
}

export default Reg;
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
        this.ch = null;
    }

    setChar(ch) {
        this.ch = ch;
    }

    // 添加一个匹配规则及流向的结点
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

class Reg {
    constructor(pattern, global = false) {
        this.pattern = pattern;
        this.global = global;
        // 从哪个坐标开始匹配，如果是全局匹配，每次匹配成功后，需要修改这个值
        this.startIndex = 0;
        this.fsm = null;
        this.parse(pattern);
    }

    parse(pattern) {
        this.fsm = new FSM();
        let count = 1;
        let first = new StateNode(count);
        this.fsm.addStateNode(first);
        let preNoStar = this.fsm.header;
        let stateNode = null;
        let prePre = null;
        for (let i = 0; i < pattern.length; i++) {
            let ch = pattern.substr(i, 1);
            if (i < pattern.length - 1) {
                if (pattern.substr(i + 1, 1) === "*") {
                    ch = pattern.substr(i, 2);
                    i++;
                }
            }
            let haveStar = ch.indexOf("*") !== -1;
            let charReal = ch.substr(0, 1);
            stateNode = new StateNode(++count);
            stateNode.setChar(ch);
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
        let te = this.fsm.tail;
        while (te !== null && te.ch && te.ch.indexOf("*") !== -1) {
            te.accept = true;
            te = te.pre;
        }
        if (te && te !== this.fsm.header) {
            te.accept = true;
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
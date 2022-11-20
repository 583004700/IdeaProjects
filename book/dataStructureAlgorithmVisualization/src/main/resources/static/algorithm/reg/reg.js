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

class CharMatchFactory {
    static getCharMatch(patternChar) {
        let result = null;
        switch (patternChar) {
            case ".":
                result = new DotCharMatch(patternChar);
                break;
            default:
                result = new CharMatch(patternChar);
                break;
        }
        return result;
    }
}

class StateNode {
    constructor(accept = false) {
        // 是否可接受
        this.accept = accept;
        this.charMatchs = {};
    }

    // 添加一个匹配规则及流向的结点
    addCharMatch(charMatch, stateNode) {
        let key = charMatch.patternChar;
        let charMatchArr = this.charMatchs[key];
        if (charMatchArr === undefined) {
            charMatchArr = [];
            this.charMatchs[key] = charMatch;
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
        this.next = null;
        this.pre = null;
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
        for (let i = 0; i < currentArr.length; i++) {
            let current = currentArr[i];
            let newCurrentArr = current.charMatchs[c];
            this.currentArr = [...this.currentArr, ...newCurrentArr];
        }
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
    }

    test(str) {

    }

    exec(str) {

    }
}

export default Reg;
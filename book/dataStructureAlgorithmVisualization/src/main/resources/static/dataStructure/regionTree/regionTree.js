import Node from "./Node";
import DrawTree from "../common/js/draw/tree/DrawTree";

class CalcMethod {

}

class AddCalcMethod extends CalcMethod {

    defaultCacheNumber() {
        return 0;
    }

    updateCalc(a, b) {
        return a + b;
    }

    calc(a, b) {
        if(b === null || b === undefined){
            b = this.defaultCacheNumber();
        }
        return a + b;
    }

    getNewValue(oldValue, cacheNum, count) {
        return this.updateCalc(oldValue, cacheNum * count);
    }
}

class MultiplyCalcMethod extends CalcMethod {

    defaultCacheNumber() {
        return 1;
    }

    updateCalc(a, b) {
        return a * b;
    }

    calc(a, b) {
        if(b === null || b === undefined){
            b = this.defaultCacheNumber();
        }
        return a * b;
    }

    getNewValue(oldValue, cacheNum, count) {
        return this.updateCalc(oldValue, Math.pow(cacheNum, count));
    }
}

class MaxCalcMethod extends CalcMethod {

    defaultCacheNumber() {
        return 0;
    }

    updateCalc(a, b) {
        return a + b;
    }

    calc(a, b) {
        if(b === null || b === undefined){
            return a;
        }
        return Math.max(a, b);
    }

    getNewValue(oldValue, cacheNum, count) {
        return this.updateCalc(oldValue, cacheNum * count);
    }
}

class MinCalcMethod extends CalcMethod {

    defaultCacheNumber() {
        return 0;
    }

    updateCalc(a, b) {
        return a + b;
    }

    calc(a, b) {
        if(b === null || b === undefined){
            return a;
        }
        return Math.min(a, b);
    }

    getNewValue(oldValue, cacheNum, count) {
        return this.updateCalc(oldValue, cacheNum * count);
    }
}

let Const = {
    opeAdd: "区间和",
    opeMultiply: '区间积',
    opeMax: '最大值',
    opeMin: '最小值'
}

// 可能策略中有多个方法，这个是根据上下文场景进行组合
class CalcMethodContext {
    constructor(ope) {
        this.ope = ope;
        if (ope === Const.opeAdd) {
            this.strategy = new AddCalcMethod();
        } else if (ope === Const.opeMultiply) {
            this.strategy = new MultiplyCalcMethod();
        } else if (ope === Const.opeMax) {
            this.strategy = new MaxCalcMethod();
        } else if (ope === Const.opeMin) {
            this.strategy = new MinCalcMethod();
        }
    }

    calc(a, b) {
        return this.strategy.calc(a, b);
    }

    getNewValue(oldValue, cacheNum, count) {
        return this.strategy.getNewValue(oldValue, cacheNum, count);
    }

    getDefaultCacheNumber() {
        return this.strategy.defaultCacheNumber();
    }
}

class RegionTree {
    // 构建线锻树
    build(arr, opt) {
        this.calcMethodContext = new CalcMethodContext(opt);
        if (!arr.length) {
            throw "数据格式不正确";
        }
        if (arr.length < 1) {
            throw "数组长度不小于1";
        }
        let nodes = [];
        for (let i = 0; i < arr.length; i++) {
            let node = new Node();
            node.cacheNumber = this.calcMethodContext.getDefaultCacheNumber();
            node.data = arr[i];
            node.startIndex = i;
            node.endIndex = i;
            nodes.push(node);
        }
        let root = this.buildParent(nodes)[0];
        this.root = root;
    }

    // 获取区间值
    getRegionValue(startIndex, endIndex) {
        if (!this.root) {
            throw "请先构建线段树";
        }
        return this.doGetRegionValue(this.root, this.calcMethodContext.getDefaultCacheNumber(), startIndex, endIndex);
    }

    // 更新区间值，如果是区间求积，是批量乘以某个数，如果是区间求和，是批量加上某个数
    update(startIndex, endIndex, value) {
        if(this.calcMethodContext.ope === Const.opeMax || this.calcMethodContext.ope === Const.opeMin){
            throw "求最小值或最大值时不支持更新！";
        }
        if (!this.root) {
            throw "请先构建线段树";
        }
        if (startIndex < this.root.startIndex || endIndex > this.root.endIndex) {
            throw "下标不能在范围之外！";
        }
        this.doUpdate(this.root, this.calcMethodContext.getDefaultCacheNumber(), startIndex, endIndex, value);
    }

    doUpdate(node, parentCacheNumber, startIndex, endIndex, value) {
        if (node.isCover(startIndex, endIndex)) {
            node.cacheNumber = this.calcMethodContext.calc(node.cacheNumber, value);
            node.cacheNumber = this.calcMethodContext.calc(node.cacheNumber, parentCacheNumber);
            return;
        }
        let cacheNumber = this.calcMethodContext.calc(node.cacheNumber, parentCacheNumber);
        node.cacheNumber = this.calcMethodContext.getDefaultCacheNumber();
        let r = this.calcMethodContext.getNewValue(node.data, cacheNumber, (node.endIndex - node.startIndex + 1));
        if (node.isMixed(startIndex, endIndex)) {
            let s = Math.max(startIndex, node.startIndex);
            let e = Math.min(endIndex, node.endIndex);
            r = this.calcMethodContext.getNewValue(r, value, (e - s + 1));
        }
        node.data = r;
        if (node.left) {
            if (node.left.isMixed(startIndex, endIndex)) {
                this.doUpdate(node.left, cacheNumber, startIndex, endIndex, value);
            } else {
                node.left.cacheNumber = this.calcMethodContext.calc(node.left.cacheNumber, cacheNumber);
            }
        }
        if (node.right) {
            if (node.right.isMixed(startIndex, endIndex)) {
                this.doUpdate(node.right, cacheNumber, startIndex, endIndex, value);
            } else {
                node.right.cacheNumber = this.calcMethodContext.calc(node.right.cacheNumber, cacheNumber);
            }
        }
    }

    // 求节点及所有子节点的值
    doGetRegionValue(node, parentCacheNumber, startIndex, endIndex) {
        let cacheNumber = this.calcMethodContext.calc(parentCacheNumber, node.cacheNumber);
        let currentResult = this.calcMethodContext.getNewValue(node.data, cacheNumber, (node.endIndex - node.startIndex + 1));
        if (node.isCover(startIndex, endIndex)) {
            return currentResult;
        }
        if (node.isMixed(startIndex, endIndex)) {
            let leftResult = null;
            let rightResult = null;
            if (node.left && node.left.isMixed(startIndex, endIndex)) {
                leftResult = this.doGetRegionValue(node.left, cacheNumber, startIndex, endIndex);
            }
            if (node.right && node.right.isMixed(startIndex, endIndex)) {
                rightResult = this.doGetRegionValue(node.right, cacheNumber, startIndex, endIndex);
            }
            let result = null;
            if (leftResult !== null && rightResult !== null) {
                result = this.calcMethodContext.calc(leftResult, rightResult);
            } else if (leftResult !== null) {
                result = this.calcMethodContext.calc(leftResult, rightResult);
            } else if (rightResult !== null) {
                result = this.calcMethodContext.calc(rightResult, leftResult);
            }
            return result;
        }
        return null;
    }

    buildParent(nodes) {
        if (nodes.length === 1) {
            return nodes;
        }
        let parents = [];
        for (let i = 0; i < nodes.length; i += 2) {
            let current = nodes[i];
            let next = null;
            let hasNext = true;
            if (i + 1 < nodes.length) {
                next = nodes[i + 1];
            }
            let parent = new Node();
            let calcMethod = this.calcMethodContext;
            parent.cacheNumber = this.calcMethodContext.getDefaultCacheNumber();
            if (next != null) {
                parent.data = calcMethod.calc(current.data, next.data);
                parent.endIndex = next.endIndex;
            } else {
                parent.data = current.data;
                parent.endIndex = current.endIndex;
            }
            parent.startIndex = current.startIndex;

            parent.left = current;
            if (hasNext) {
                parent.right = next;
            }
            parents.push(parent);
        }
        let ps = this.buildParent(parents);
        if (ps) {
            return ps;
        }
        if (parents.length <= 1) {
            return parents;
        }
    }

    getHeight() {
        return this.doHeight(this.root);
    }

    /**
     * 获取树的高度
     * @param node
     * @returns {number}
     */
    doHeight(node) {
        if (!node) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        let leftHeight = this.doHeight(node.left);
        let rightHeight = this.doHeight(node.right);
        let max = Math.max(leftHeight, rightHeight);
        return max + 1;
    }

    // 优化画图逻辑，从最后一层开始画，节点不会再重叠在一起
    drawTreeOptimize(ctx, startX, startY, startSplitY) {
        ctx.clearRect(0, 0, 10000000, 1000000);
        if (this.root) {
            DrawTree.drawNodeOptimize(ctx, this.root, startX, startY, startSplitY);
        }
    }
}

export default RegionTree;
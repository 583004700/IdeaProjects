import Node from "./Node";
import DrawTree from "../common/js/draw/tree/DrawTree";

class CalcMethod {
    calc(a, b) {
        throw "请重写计算方式";
    }

    update(a, b, val) {
        throw "请重写操作方式，比如乘5，比如加3等";
    }
}

class AddCalcMethod extends CalcMethod {

    defaultCacheNumber() {
        return 0;
    }

    calc(a, b) {
        if (b === null || b === undefined) {
            b = 0;
        }
        return a + b;
    }

    // 加法增加后，父节点累计相加
    update(a, b, val) {
        if (b === null || b === undefined) {
            b = 0;
        }
        return (a + val) + (b + val);
    }
}

class MultiplyCalcMethod extends CalcMethod {

    defaultCacheNumber() {
        return 1;
    }

    calc(a, b) {
        if (b === null || b === undefined) {
            b = 1;
        }
        return a * b;
    }

    // 乘法相乘，父节点扩大平方倍
    update(a, b, val) {
        if (b === null || b === undefined) {
            b = 1;
        }
        return a * b * val * val;
    }
}

let Const = {
    opeAdd: "区间和",
    opeMultiply: '区间积'
}

// 可能策略中有多个方法，这个是根据上下文场景进行组合
class CalcMethodContext {
    constructor(ope) {
        if (ope === Const.opeAdd) {
            this.strategy = new AddCalcMethod();
        } else if (ope === Const.opeMultiply) {
            this.strategy = new MultiplyCalcMethod();
        }
    }

    calc(a, b) {
        return this.strategy.calc(a, b);
    }

    update(a, b, val) {
        return this.strategy.update(a, b, val);
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
        return this.doGetRegionValue(this.root, startIndex, endIndex);
    }

    // 更新区间值，如果是区间求积，是批量乘以某个数，如果是区间求和，是批量加上某个数
    update(startIndex, endIndex, value) {
        if (!this.root) {
            throw "请先构建线段树";
        }
        if(startIndex < this.root.startIndex || endIndex > this.root.endIndex){
            throw "下标不能在范围之外！";
        }
        this.doUpdate(this.root, startIndex, endIndex, value);
    }

    doUpdate(node, startIndex, endIndex, value) {
        if (node.isCover(startIndex, endIndex)) {
            let result = (endIndex - startIndex + 1) * value
            node.cacheNumber = this.calcMethodContext.calc(node.cacheNumber, result);
            return result;
        }
        if (node.isMixed(startIndex, endIndex)) {
            let leftResult = this.calcMethodContext.getDefaultCacheNumber();
            let rightResult = this.calcMethodContext.getDefaultCacheNumber();
            if (node.left && node.left.isMixed(startIndex, endIndex)) {
                leftResult = this.doUpdate(node.left, startIndex, endIndex, value);
            }
            if (node.right && node.right.isMixed(startIndex, endIndex)) {
                rightResult = this.doUpdate(node.right, startIndex, endIndex, value);
            }
            let r1 = this.calcMethodContext.calc(leftResult, rightResult);
            let result = this.calcMethodContext.calc(node.cacheNumber, r1);
            node.cacheNumber = result;
            return r1;
        }
    }

    // 求节点及所有子节点的值
    doGetRegionValue(node, startIndex, endIndex) {
        if (node.isCover(startIndex, endIndex)) {
            let result = this.calcMethodContext.calc(node.data, node.cacheNumber);
            return result;
        }
        if (node.isMixed(startIndex, endIndex)) {
            let leftResult = null;
            let rightResult = null;
            if (node.left && node.left.isMixed(startIndex, endIndex)) {
                leftResult = this.doGetRegionValue(node.left, startIndex, endIndex);
            }
            if (node.right && node.right.isMixed(startIndex, endIndex)) {
                rightResult = this.doGetRegionValue(node.right, startIndex, endIndex);
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
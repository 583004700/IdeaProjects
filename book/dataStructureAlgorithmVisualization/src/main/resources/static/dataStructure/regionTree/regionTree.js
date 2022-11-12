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

    calc(a, b) {
        return a + b;
    }

    // 加法增加后，父节点累计相加
    update(a, b, val) {
        return (a + val) + (b + val);
    }
}

class MultiplyCalcMethod extends CalcMethod {

    calc(a, b) {
        return a * b;
    }

    // 乘法相乘，父节点扩大平方倍
    update(a, b, val) {
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
}

class RegionTree {
    // 构建线锻树
    build(arr,opt) {
        this.opt = opt;
        if (!arr.length) {
            throw "数据格式不正确";
        }
        if (arr.length < 1) {
            throw "数组长度不小于1";
        }
        let nodes = [];
        for (let i = 0; i < arr.length; i++) {
            let node = new Node();
            node.data = arr[i];
            node.startIndex = i;
            node.endIndex = i;
            nodes.push(node);
        }
        let root = this.buildParent(nodes)[0];
        this.root = root;
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
            let calcMethod = new CalcMethodContext(this.opt);
            if(next != null){
                parent.data = calcMethod.calc(current.data,next.data);
                parent.endIndex = next.endIndex;
            }else{
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
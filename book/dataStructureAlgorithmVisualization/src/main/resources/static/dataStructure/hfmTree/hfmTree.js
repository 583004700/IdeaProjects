import Node from "./Node";
import DrawTree from "../common/js/draw/tree/DrawTree";

class HfmTree {
    constructor() {
        this.nodes = [];
    }

    // 优化画图逻辑，从最后一层开始画，节点不会再重叠在一起
    drawTreeOptimize(ctx, startX, startY, startSplitY) {
        ctx.clearRect(0, 0, 10000000, 1000000);
        if (this.root) {
            DrawTree.drawNodeOptimize(ctx, this.root, startX, startY, startSplitY);
        }
    }

    /**
     * 得到每个节点对应的编码
     */
    getCodes() {
        if(this.nodes.length === 1){
            // 如果只有一个节点，直接使用编码0
            return this.doGetCodes(this.root,"0");
        }
        return this.doGetCodes(this.root, "");
    }

    doGetCodes(node, lr) {
        let result = "";
        if (node != null && node.isValid()) {
            result += (node.data + "->" + lr+"\n");
        }
        if (node.left != null) {
            result += this.doGetCodes(node.left, lr + "0");
        }
        if (node.right != null) {
            result += this.doGetCodes(node.right, lr + "1");
        }
        return result;
    }

    delete(data) {
        let target = this.search(data);
        if (target) {
            let nodes = this.nodes;
            nodes.remove(target);
            this.build();
        }
    }

    add(data, weight) {
        this.doAdd(data, weight);
    }

    build() {
        let nodes = [].addAll(this.nodes);
        while (nodes.size() > 1) {
            nodes.sort(function (a, b) {
                return a.weight - b.weight;
            });
            let a = nodes.removeIndex(0);
            let b = nodes.removeIndex(0);
            let ab = new Node();
            ab.setWeight(a.weight + b.weight);
            ab.setLeft(a);
            ab.setRight(b);
            ab.data = a.weight + b.weight;
            nodes.add(ab);
        }
        this.root = nodes.get(0);
    }

    doAdd(data, weight) {
        let nodes = this.nodes;
        let node = new Node(data, weight);
        node.setBlack();
        nodes.add(node);
        this.build();
    }

    /**
     * 查找节点
     * @param data
     */
    search(data) {
        return this.doSearch(data);
    }

    doSearch(data) {
        for (let i = 0; i < this.nodes.length; i++) {
            let node = this.nodes[i];
            if(node.isValid() && node.data === data){
                return node;
            }
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
}

export default HfmTree;
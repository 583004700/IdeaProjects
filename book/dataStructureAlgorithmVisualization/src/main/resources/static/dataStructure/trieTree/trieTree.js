import Node from './Node';
import DrawTree from "../common/js/draw/tree/DrawTree";

class TrieTree {
    constructor() {
        this.root = new Node();
    }

    insert(word) {
        let wordArr = word.split("");
        let current = this.root;
        let index = 0;
        while (index < wordArr.length) {
            if (!current.children.has(wordArr[index])) {
                let child = new Node();
                child.ch = wordArr[index];
                current.children.set(child.ch, child);
            }
            current = current.children.get(wordArr[index]);
            if (index === wordArr.length - 1) {
                current.setIsTail(true);
            }
            index++;
        }
    }

    _searchNode(word) {
        let wordArr = word.split("");
        let current = this.root;
        let index = 0;
        while (index < wordArr.length && current) {
            current = current.children.get(wordArr[index]);
            index++;
        }
        if (current && current.ch) {
            return current;
        }
        return null;
    }

    search(word) {
        let node = this._searchNode(word);
        return node !== null && node.isTail === true;
    }

    startsWith(prefix) {
        return this._searchNode(prefix) !== null;
    }

    // 优化画图逻辑，从最后一层开始画，节点不会再重叠在一起
    drawTreeOptimize(ctx, startX, startY, startSplitY) {
        ctx.clearRect(0, 0, 10000000, 1000000);
        if (this.root) {
            DrawTree.drawNodeOptimize(ctx, this.root, startX, startY, startSplitY);
        }
    }

    getHeight() {
        return this.doHeight(this.root);
    }

    doHeight(node) {
        if (!node) {
            return 0;
        }
        if (node.getChildrenList().length === 0) {
            return 1;
        }
        let max = 0;
        for (let i = 0; i < node.getChildrenList().length; i++) {
            let childHeight = this.doHeight(node.getChildrenList().get(i));
            if (childHeight > max) {
                max = childHeight;
            }
        }
        return max + 1;
    }
}

export default TrieTree;
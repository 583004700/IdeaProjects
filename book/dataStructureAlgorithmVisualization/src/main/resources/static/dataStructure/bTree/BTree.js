import Node from './Node';
import DrawTree from "../common/js/draw/tree/DrawTree";

class BTree {
    constructor(order) {
        this.order = order;
        this.minDataLength = Math.ceil(this.order / 2) - 1;
        this.root = null;
    }

    add(data) {
        if (this.search(data) != null) {
            // 节点已经存在
            return;
        }
        if (this.root == null) {
            let node = Node.getInstance(data, this.order);
            this.root = node;
        } else {
            let tNode = this.searchInsertNode(data);
            // 如果上溢
            let up = tNode.add(data);
            while (up) {
                let listNode = up.getList();
                let pNode = this.searchParentNode(tNode);
                if (pNode == null) {
                    pNode = new Node(this.order);
                    this.root = pNode;
                }
                pNode.removeChildNode(tNode);
                pNode.addChildNode(listNode.get(0));
                pNode.addChildNode(listNode.get(1));
                tNode = pNode;
                up = pNode.add(up.getValue());
            }
        }
    }

    // 优化画图逻辑，从最后一层开始画，节点不会再重叠在一起
    drawTreeOptimize(ctx, startX, startY, startSplitY) {
        ctx.clearRect(0, 0, 10000000, 1000000);
        if (this.root) {
            DrawTree.drawNodeOptimize(ctx, this.root, startX, startY, startSplitY);
        }
    }

    delete(data) {
        let tNode = this.searchNode(data);
        if (tNode == null) {
            return;
        }
        if (this.root.getDataList().length === 1 && this.root.getChildrenList().length === 0) {
            this.root = null;
            return;
        }
        if (this.root.getChildrenList().length === 0) {
            this.root.removeData(data);
            return;
        }
        if (tNode.getLeftNode() != null) {
            let leftMax = this.getMax(tNode.getLeftNode(data));
            let leftMaxData = leftMax.getMax();
            tNode.removeData(data);
            tNode.add(leftMaxData);

            if (leftMax.getDataList().length === this.minDataLength) {
                this.down(leftMax);
            }
            leftMax.removeData(leftMaxData);
        } else {
            if (tNode.getDataList().length === this.minDataLength) {
                this.down(tNode);
            }
            tNode.removeData(data);
        }
    }

    down(node) {
        let leftBrother = this.getLeftBrother(node);
        let rightBrother = this.getRightBrother(node);
        let parent = this.searchParentNode(node);
        let borrow = leftBrother != null ? leftBrother : rightBrother;
        if (borrow !== rightBrother && rightBrother != null && rightBrother.getDataList().length > this.minDataLength) {
            borrow = rightBrother;
        }
        let borrowData = null;
        let parentIndex = -1;
        let borrowNode = null;
        if (borrow === leftBrother) {
            borrowData = leftBrother.getMax();
            parentIndex = parent.searchNodeIndex(node) - 1;
            borrowNode = borrow.getRightNode();
        } else {
            borrowData = rightBrother.getMin();
            parentIndex = parent.searchNodeIndex(node);
            borrowNode = borrow.getLeftNode();
        }
        let parentData = parent.getDataList().get(parentIndex);
        if (borrow.getDataList().length > this.minDataLength) {
            node.add(parentData);
            parent.removeData(parentData);
            parent.add(borrowData);
            borrow.removeData(borrowData);
            if (borrowNode != null) {
                borrow.removeChildNode(borrowNode);
                node.addChildNode(borrowNode);
            }
        } else {
            borrow.add(parentData);
            if (node.getChildrenList().length > 0) {
                for (let i = 0; i < node.getChildrenList().length; i++) {
                    let tNode = node.getChildrenList().get(i);
                    borrow.addChildNode(tNode);
                }
            }
            parent.removeChildNode(node);
            if (parent.getDataList().length === 1 && parent !== this.root) {
                this.down(parent);
            }
            if (parent === this.root && this.root.getChildrenList().length === 1) {
                this.root = borrow;
            }
            parent.removeData(parentData);
        }
    }

    getRightBrother(node) {
        let parent = this.searchParentNode(node);
        if (parent != null) {
            let index = parent.searchNodeIndex(node);
            if (index < parent.getChildrenList().length - 1) {
                return parent.getChildrenList().get(index + 1);
            }
        }
        return null;
    }

    getLeftBrother(node) {
        let parent = this.searchParentNode(node);
        if (parent != null) {
            let index = parent.searchNodeIndex(node);
            if (index > 0) {
                return parent.getChildrenList().get(index - 1);
            }
        }
        return null;
    }

    search(data) {
        return this.doSearch(this.root, data);
    }

    getMax(start) {
        let result = start;
        while (result.getRightNode() != null) {
            result = result.getRightNode();
        }
        return result;
    }

    searchNode(data) {
        return this.doSearchNode(this.root, data);
    }

    searchParentNode(node) {
        return this.doSearchParentNode(this.root, node);
    }

    searchInsertNode(data) {
        return this.doSearchInsertNode(this.root, data);
    }

    doSearch(start, data) {
        if (start == null) {
            return null;
        }
        let min = start.getMin();
        let max = start.getMax();
        if (data.compareTo(min) < 0) {
            let left = start.getLeftNode(min);
            if (left != null) {
                return this.doSearch(left, data);
            }
        } else if (data.compareTo(max) > 0) {
            let right = start.getRightNode(max);
            if (right != null) {
                return this.doSearch(right, data);
            }
        } else {
            let i = start.searchIndex(data);
            if (i !== -1) {
                return start.getDataList().get(i);
            }
            let index = start.searchInsertIndex(data);
            if (start.getChildrenList().length !== 0) {
                let searchNode = start.getChildrenList().get(index);
                return this.doSearch(searchNode, data);
            }
        }
        return null;
    }

    doSearchNode(start, data) {
        if (start == null) {
            return null;
        }
        let min = start.getMin();
        let max = start.getMax();
        if (data.compareTo(min) < 0) {
            let left = start.getLeftNode(min);
            if (left != null) {
                return this.doSearchNode(left, data);
            }
        } else if (data.compareTo(max) > 0) {
            let right = start.getRightNode(max);
            if (right != null) {
                return this.doSearchNode(right, data);
            }
        } else {
            let i = start.searchIndex(data);
            if (i !== -1) {
                return start;
            }
            let index = start.searchInsertIndex(data);
            if (start.getChildrenList().length !== 0) {
                let searchNode = start.getChildrenList().get(index);
                return this.doSearchNode(searchNode, data);
            }
        }
        return null;
    }

    doSearchParentNode(start, node) {
        if (start.getChildrenList().contains(node)) {
            return start;
        }
        for (let i = 0; i < start.getChildrenList().length; i++) {
            let tNode = start.getChildrenList().get(i);
            let p = this.doSearchParentNode(tNode, node);
            if (p != null) {
                return p;
            }
        }
        return null;
    }

    doSearchInsertNode(start, data) {
        let min = start.getMin();
        let max = start.getMax();
        if (data.compareTo(min) < 0) {
            let left = start.getLeftNode(min);
            if (left != null) {
                return this.doSearchInsertNode(left, data);
            }
        } else if (data.compareTo(max) > 0) {
            let right = start.getRightNode(max);
            if (right != null) {
                return this.doSearchInsertNode(right, data);
            }
        } else {
            let index = start.searchInsertIndex(data);
            if (start.getChildrenList().length !== 0) {
                let searchNode = start.getChildrenList().get(index);
                return this.doSearchInsertNode(searchNode, data);
            } else {
                return start;
            }
        }
        return start;
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

    printTree() {
        if (!this.root) {
            return;
        }
        let map = {};
        let first = [];
        first.add(this.root);
        map[1] = first;
        let level = 1;
        while (map[level]) {
            let nodes = map[level];
            let nextKey = level + 1;
            for (let i = 0; i < nodes.length; i++) {
                let node = nodes.get(i);
                if (!map[nextKey]) {
                    map[nextKey] = [];
                }
                let nextNodes = map[nextKey];
                nextNodes.addAll(node.getChildrenList());
            }
            level++;
        }
        level = 1;
        while (map[level]) {
            let nodes = map[level];
            let s = "";
            for (let i = 0; i < nodes.length; i++) {
                let node = nodes.get(i);
                s += node.toString();
            }
            console.log(s);
            level++;
        }
    }

}

export default BTree;
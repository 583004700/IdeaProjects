import Node from './Node'
import DrawTree from "../common/js/draw/tree/DrawTree";

class BinarySearchTree {
    // root

    printTree() {
        if (!this.root) {
            return;
        }
        let map = {};
        let first = [];
        first.push(this.root);
        map[1] = first;
        let level = 1;
        while (map[level]) {
            let nodes = map[level];
            let nextKey = level + 1;
            for (let i = 0; i < nodes.length; i++) {
                let node = nodes[i];
                if (!map[nextKey] && (node.left != null || node.right != null)) {
                    map[nextKey] = [];
                }
                let nextNodes = map[nextKey];
                if (node.left != null) {
                    nextNodes.push(node.left);
                }
                if (node.right != null) {
                    nextNodes.push(node.right);
                }
            }
            level++;
        }
        level = 1;
        while (map[level]) {
            let nodes = map[level];
            let s = "";
            for (let i = 0; i < nodes.length; i++) {
                let node = nodes[i];
                s += node.data + "  ";
            }
            console.log(s);
            level++;
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
        let target = this.search(data);
        if (target) {
            let parent = this.searchParent(target.data);
            if (target.left == null && target.right == null) {
                if (target === this.root) {
                    this.root = null;
                    return;
                }
                if (parent.left && data.compareTo(parent.left.data) === 0) {
                    parent.left = null;
                } else if (parent.right && data.compareTo(parent.right.data) === 0) {
                    parent.right = null;
                }
            } else if (target.left != null) {
                let leftMax = this.doSearchMax(target.left);
                let leftMaxData = leftMax.data;
                this.delete(leftMaxData);
                target.data = leftMaxData;
            } else {
                let rightMin = this.doSearchMin(target.right);
                let rightMinData = rightMin.data;
                this.delete(rightMinData);
                target.data = rightMinData;
            }
        }
    }

    searchParent(data) {
        let t = new Node();
        t.data = data;
        return this.doSearchParent(this.root, t);
    }

    doSearchParent(start, node) {
        if (start == null || node == null || node.data == null) {
            return null;
        }
        let result = null;
        if (start.left != null) {
            if (node.data.compareTo(start.left.data) === 0) {
                return start;
            }
            result = this.doSearchParent(start.left, node);
            if (result != null) {
                return result;
            }
        }
        if (start.right != null) {
            if (node.data.compareTo(start.right.data) === 0) {
                return start;
            }
            return this.doSearchParent(start.right, node);
        }
        return null;
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

    /**
     * 查找最小的节点
     * @param node
     */
    doSearchMin(node) {
        if (!node) {
            return null;
        }
        let min = node;
        while (min.left) {
            min = min.left;
        }
        return min;
    }

    /**
     * 查找最大的节点
     * @param node
     */
    doSearchMax(node) {
        if (!node) {
            return null;
        }
        let max = node;
        while (max.right) {
            max = max.right;
        }
        return max;
    }

    /**
     * 查找节点
     * @param data
     */
    search(data) {
        return this.doSearch(this.root, data);
    }

    doSearch(node, data) {
        if (!node || !data) {
            return null;
        }
        if (data.compareTo(node.data) < 0) {
            if (node.left != null) {
                return this.doSearch(node.left, data);
            } else {
                return null;
            }
        } else if (data.compareTo(node.data) > 0) {
            if (node.right != null) {
                return this.doSearch(node.right, data);
            } else {
                return null;
            }
        } else {
            return node;
        }
    }

    add(data) {
        this.doAdd(this.root, data);
    }

    doAdd(node, data) {
        if (this.root == null) {
            let temp = new Node();
            temp.data = data;
            this.root = temp;
        } else {
            let temp = null;
            if (data.compareTo(node.data) < 0) {
                if (node.left != null) {
                    this.doAdd(node.left, data);
                } else {
                    temp = new Node();
                    temp.data = data;
                    node.left = temp;
                }
            } else if (data.compareTo(node.data) > 0) {
                if (node.right != null) {
                    this.doAdd(node.right, data);
                } else {
                    temp = new Node();
                    temp.data = data;
                    node.right = temp;
                }
            }
        }
    }
}
export default BinarySearchTree;
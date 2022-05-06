class BPTree {
    constructor(order) {
        this.root = null;
        this.order = order;
        this.minDataLength = Math.ceil(this.order / 2);
    }

    add(data) {
        if (this.search(data) != null) {
            // 节点已经存在
            return;
        }
        if (this.root == null) {
            let node = Node.getInstance(data, order);
            this.root = node;
        } else {
            let tNode = this.searchInsertNode(data);
            // 如果上溢
            let up = this.addData(tNode, data);
            if (up != null) {
                let n1 = up.getList().get(0);
                let n2 = up.getList().get(1);
                n1.setNext(n2);
                let leftBrother = tNode.getPre();
                if (leftBrother != null) {
                    leftBrother.setNext(n1);
                }
                let rightBrother = tNode.getNext();
                n2.setNext(rightBrother);
            }
            while (up != null) {
                let listNode = up.getList();
                let pNode = this.searchParentNode(tNode);
                if (pNode == null) {
                    pNode = new Node(this.order);
                    this.root = pNode;
                    pNode.add(listNode.get(1).getMax());
                }
                pNode.removeChildNode(tNode);
                pNode.addChildNode(listNode.get(0));
                pNode.addChildNode(listNode.get(1));
                tNode = pNode;
                up = pNode.add(up.getValue());
            }
        }
    }

    drawTree(ctx, startX, startY, startSplitX, startSplitY) {
        ctx.clearRect(0, 0, 100000, 10000);
        if (this.root) {
            ctx.beginPath();
            this.drawNode(ctx, this.root, startX, startY, startSplitX, startSplitY);
        }
    }

    drawNode(ctx, node, x, y, splitX, splitY) {
        ctx.save();
        ctx.beginPath();
        let width = node.getDataList().toString().length * 6;
        width = Math.max(width, 15);
        let height = 15;
        node.x = x;
        node.y = y;
        node.width = width;
        node.height = height;
        ctx.ellipse(x, y, width, height, 0, 0, 2 * Math.PI);
        ctx.font = "15px 微软雅黑";
        if (node.color) {
            ctx.strokeStyle = node.color;
        } else {
            ctx.strokeStyle = "black";
        }
        let sub = node.getDataList().toString().length * 5;
        ctx.strokeText(node.getDataList().toString(), x - sub, y + 6);
        ctx.stroke();

        if (node.getPre()) {
            ctx.strokeStyle="red";
            let pre = node.getPre();
            ctx.beginPath();
            ctx.moveTo(pre.x + pre.width, y);
            ctx.lineTo(node.x - node.width, y);
            ctx.stroke();
            ctx.beginPath();
            ctx.moveTo(node.x - node.width - 12, y + 5);
            ctx.lineTo(node.x - node.width, y);
            ctx.stroke();
            ctx.beginPath();
            ctx.moveTo(node.x - node.width - 12, y - 5);
            ctx.lineTo(node.x - node.width, y);
            ctx.stroke();
        }

        ctx.restore();
        ctx.beginPath();
        let length = node.getChildrenList().length;
        let leftPosition = -(splitX / 2);
        splitX = splitX / (length - 1);
        let preEndPosition = null;
        for (let i = 0; i < length; i++) {
            let p = leftPosition + i * splitX;
            let childNode = node.getChildrenList()[i];
            let childNodeWidth = childNode.getDataList().toString().length * 6;
            childNodeWidth = Math.max(childNodeWidth, 15);
            let xPosition = x + p;
            if (xPosition < preEndPosition + childNodeWidth) {
                //防止节点重叠在一起
                xPosition = preEndPosition + childNodeWidth;
            }
            ctx.moveTo(x, y + 13);
            ctx.lineTo(xPosition, y - 13 + splitY);
            ctx.stroke();
            preEndPosition = this.drawNode(ctx, childNode, xPosition, y + splitY, splitX / 2, splitY);
        }
        return width + x;
    }

    delete(data) {
        let tNode = this.searchNode(data);
        if (tNode == null) {
            return;
        }
        if (this.root.getDataList().size() === 1 && this.root.getChildrenList().size() === 0) {
            this.root = null;
            return;
        }
        if (this.root.getChildrenList().size() === 0) {
            this.root.removeData(data);
            return;
        }
        this.removeData(tNode, data);
        if (tNode.getDataList().size() < this.minDataLength) {
            this.down(tNode);
        }
    }

    removeData(node, data) {
        let isMax = data.compareTo(node.getMax()) === 0;
        node.removeData(data);
        let maxV = node.getMax();
        let isOne = node.getDataList().size() === 0;
        if (isOne) {
            maxV = data;
        }
        // 如果移除的点是最大值，则要更新所有父节点的最大值
        let temp = node;
        while (temp != null && isMax) {
            let p = this.searchParentNode(temp);
            if (p != null) {
                let i = p.searchNodeIndex(temp, false);
                //判断更新的父结点是不是最大值,必须放在设置值之前判断
                isMax = data.compareTo(p.getMax()) === 0;
                if (!isOne) {
                    p.getDataList().set(i, maxV);
                } else {
                    p.getDataList().removeIndex(i);
                    p.getChildrenList().removeIndex(i);
                }
                isOne = p.getDataList().size() === 0;
            }
            temp = p;
        }
    }

    addData(node, data) {
        let isMax = data.compareTo(node.getMax()) > 0;
        // 如果插入的点是最大值，则要更新所有父节点的最大值
        let temp = node;
        while (temp != null && isMax) {
            let p = this.searchParentNode(temp);
            if (p != null) {
                let i = p.searchNodeIndex(temp, false);
                isMax = data.compareTo(p.getMax()) > 0;
                p.getDataList().set(i, data);
            }
            temp = p;
        }
        return node.add(data);
    }

    down(node) {
        let leftBrother = node.getPre();
        let rightBrother = node.getNext();
        let parent = this.searchParentNode(node);
        let borrow = leftBrother != null ? leftBrother : rightBrother;
        if (borrow == null) {
            leftBrother = this.getLeftBrother(node);
            rightBrother = this.getRightBrother(node);
            borrow = leftBrother != null ? leftBrother : rightBrother;
        }
        let borrowData = null;
        let borrowNode = null;
        if (borrow !== rightBrother && rightBrother != null && rightBrother.getDataList().size() > this.minDataLength) {
            borrow = rightBrother;
        }
        if (borrow === leftBrother) {
            borrowData = leftBrother.getMax();
            borrowNode = leftBrother.getRightNode();
        } else {
            borrowData = rightBrother.getMin();
            borrowNode = rightBrother.getLeftNode();
        }
        if (borrow.getDataList().size() > this.minDataLength) {
            this.addData(node, borrowData);
            this.removeData(borrow, borrowData);
            if (borrowNode != null) {
                node.addChildNode(borrowNode);
                borrow.removeChildNode(borrowNode);
            }
        } else {
            //int count = node.getDataList().size() - 1;
            let count = 0;
            for (let i = 0; i < node.getDataList().size();) {
                let t = node.getDataList().get(i);
                this.addData(borrow, t);
                if (node.getChildrenList().size() > 0) {
                    borrow.addChildNode(node.getChildrenList().get(count++));
                }
                this.removeData(node, t);
            }
            if (parent.getDataList().size() === 1 && parent === this.root) {
                this.root = parent.getLeftNode();
            } else if (parent.getDataList().size() < this.minDataLength) {
                if (parent !== this.root) {
                    this.down(parent);
                }
            }
            // 需要维护next
            if (node.getPre() != null) {
                node.getPre().setNext(node.getNext());
            } else if (node.getNext() != null) {
                node.getNext().setPre(null);
            }
        }
    }

    getRightBrother(node) {
        let parent = this.searchParentNode(node);
        if (parent != null) {
            let index = parent.searchNodeIndex(node, true);
            if (index < parent.getChildrenList().size() - 1) {
                return parent.getChildrenList().get(index + 1);
            }
        }
        return null;
    }

    getLeftBrother(node) {
        let parent = this.searchParentNode(node);
        if (parent != null) {
            let index = parent.searchNodeIndex(node, true);
            if (index > 0) {
                return parent.getChildrenList().get(index - 1);
            }
        }
        return null;
    }

    search(data) {
        return this.doSearch(this.root, data);
    }

    searchScope(min, max) {
        let startNode = this.searchInsertNode(min);
        let list = startNode.getScope(min, max);
        return list;
    }

    getMin(start) {
        let result = start;
        while (result.getLeftNode() != null) {
            result = result.getLeftNode();
        }
        return result;
    }

    getMax(start) {
        // 没有子节点元素会比当前节点元素大
        return start;
    }

    getLeftNode(data) {
        let node = this.searchNode(data);
        if (node != null) {
            return node.getLeftNode(data);
        }
        return null;
    }

    getRightNode(data) {
        let node = this.searchNode(data);
        if (node != null) {
            return node.getRightNode(data);
        }
        return null;
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
            return null;
        } else {
            let i = start.searchIndex(data);
            if (i !== -1 && start.getChildrenList().size() === 0) {
                return start.getDataList().get(i);
            }
            let index = start.searchInsertIndex(data);
            if (start.getChildrenList().size() !== 0) {
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
            let left = start.getLeftNode();
            if (left != null) {
                return this.doSearchNode(left, data);
            }
        } else if (data.compareTo(max) > 0) {
            return null;
        } else {
            let i = start.searchIndex(data);
            // 这里与B树不同，B+树所有数据在叶子节点
            if (i !== -1 && start.getChildrenList().size() === 0) {
                return start;
            }
            let index = start.searchInsertIndex(data);
            if (start.getChildrenList().size() !== 0) {
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
        for (let i = 0; i < start.getChildrenList().size(); i++) {
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
            let left = start.getLeftNode();
            if (left != null) {
                return this.doSearchInsertNode(left, data);
            }
        } else if (data.compareTo(max) > 0) {
            let right = start.getRightNode();
            if (right != null) {
                return this.doSearchInsertNode(right, data);
            }
        } else {
            let index = start.searchInsertIndex(data);
            if (start.getChildrenList().size() !== 0) {
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
        if (this.root == null) {
            return;
        }
        let map = {};
        let first = [];
        first.add(this.root);
        map.put(1, first);
        let level = 1;
        while (map.containsKey(level)) {
            let nodes = map.get(level);
            let nextKey = level + 1;
            for (let i = 0; i < nodes.size(); i++) {
                let node = nodes.get(i);
                if (!map.containsKey(nextKey)) {
                    map.put(nextKey, []);
                }
                let nextNodes = map.get(nextKey);
                nextNodes.addAll(node.getChildrenList());
            }
            level++;
        }
        level = 1;
        while (map.containsKey(level)) {
            let nodes = map.get(level);
            let s = "";
            for (let i = 0; i < nodes.size(); i++) {
                let node = nodes.get(i);
                s += node.toString();
            }
            console.log(s);
            level++;
        }
    }
}
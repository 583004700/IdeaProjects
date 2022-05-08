class Status {
    setX(x) {
        this.x = x;
    }

    getX() {
        return this.x;
    }

    setMiddle(middle) {
        this.middle = middle;
    }

    getMiddle() {
        return this.middle;
    }

    setY(y) {
        this.y = y;
    }

    getY() {
        return this.y;
    }

    setWidth(width) {
        this.width = width;
    }

    getWidth() {
        return this.width;
    }

    setNode(node) {
        this.node = node;
    }

    getNode() {
        return this.node;
    }
}

class RBTree {
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
            this.drawNodeOptimize(ctx, this.root, startX, startY, startSplitY);
        }
    }

    drawNodeOptimize(ctx, node, x, y, splitY) {
        ctx.save();
        let childrenList = [];
        if (node.left != null) {
            childrenList.add(node.left);
        }
        if (node.right != null) {
            childrenList.add(node.right);
        }

        let length = childrenList.length;
        let statusList = [];
        let allWidth = 0;
        let splitX = 30;
        for (let i = 0; i < length; i++) {
            let childNode = childrenList[i];
            let pre = null;
            let xx = x;
            let width = 0;
            if (i - 1 >= 0) {
                pre = statusList.get(i - 1);
                xx = pre.getX();
                width = pre.getWidth();
            }
            let s = this.drawNodeOptimize(ctx, childNode, xx + width * 2 + splitX, y + splitY, splitY);
            statusList.add(s);
        }

        let nodeWidth = node.data.toString().length * 6;
        nodeWidth = Math.max(nodeWidth, 15);
        let height = 15;
        let currentX = x + nodeWidth;
        if (statusList.size() > 0) {
            let first = statusList.get(0);
            let last = statusList.get(statusList.size() - 1);
            if (first.getNode() === last.getNode()) {
                // 只有一个结点时
                if (first.getNode() === node.left) {
                    currentX = first.getMiddle() + splitX / 2;
                } else if (first.getNode() === node.right) {
                    currentX = first.getMiddle() - splitX / 2;
                }
            } else {
                // 有两个以上结点时
                currentX = (first.getMiddle() + last.getMiddle()) / 2;
            }
            let currentY = y + height;
            for (let i = 0; i < statusList.size(); i++) {
                let s = statusList.get(i);
                ctx.beginPath();
                ctx.moveTo(currentX, currentY);
                ctx.lineTo(s.getMiddle(), s.getY() - height);
                ctx.strokeStyle = s.getNode().color;
                ctx.stroke();
                allWidth += s.getWidth() + splitX / 2;
            }
        }
        ctx.beginPath();
        ctx.ellipse(currentX, y, nodeWidth, height, 0, 0, 2 * Math.PI);
        ctx.font = "15px 微软雅黑";
        if (node.color) {
            ctx.strokeStyle = node.color;
        } else {
            ctx.strokeStyle = "black";
        }
        let sub = node.data.toString().length * 5;
        ctx.strokeText(node.data.toString(), currentX - sub, y + 6);
        ctx.stroke();
        ctx.restore();
        let status = new Status();
        status.setX(x);
        status.setY(y);
        status.setMiddle(currentX);
        status.setNode(node);
        if (statusList.size() === 0) {
            status.setWidth(nodeWidth);
        } else {
            status.setWidth(allWidth);
        }
        return status;
    }

    delete(data) {
        let target = this.search(data);
        let route = null;
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
                route = parent;
                while (route != null) {
                    let balance = this.isBalance(route);
                    if (balance != null) {
                        this.balance(balance);
                    }
                    route = this.searchParent(route.data);
                }
            } else if (target.left != null) {
                let leftMax = this.doSearchMax(target.left);
                this.delete(leftMax.data);
                target.data = leftMax.data;
            } else {
                let rightMin = this.doSearchMin(target.right);
                this.delete(rightMin.data);
                target.data = rightMin.data;
            }
        }
    }

    LL(node) {
        let left = node.left;
        let leftRight = left.right;
        left.right = node;
        node.left = leftRight;
        let parent = this.searchParent(node.data);
        if (parent != null) {
            if (node === parent.left) {
                parent.left = left;
            } else if (node === parent.right) {
                parent.right = left;
            }
        } else {
            this.root = left;
        }
        return left;
    }

    RR(node) {
        let right = node.right;
        let rightLeft = right.left;
        right.left = node;
        node.right = rightLeft;
        let parent = this.searchParent(node.data);
        if (parent != null) {
            if (node === parent.left) {
                parent.left = right;
            } else if (node === parent.right) {
                parent.right = right;
            }
        } else {
            this.root = right;
        }
        return right;
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
            temp.setBlack();
            this.root = temp;
        } else {
            let temp = null;
            if (data.compareTo(node.data) < 0) {
                if (node.left != null) {
                    this.doAdd(node.left, data);
                } else {
                    temp = new Node();
                    node.left = temp;
                }
            } else if (data.compareTo(node.data) > 0) {
                if (node.right != null) {
                    this.doAdd(node.right, data);
                } else {
                    temp = new Node();
                    node.right = temp;
                }
            }
            if (temp != null) {
                temp.data = data;
                temp.setRed();
                // 父节点
                let parent = this.searchParent(temp.data);
                // 祖父节点
                let grandParent = this.searchParent(parent.data);
                while(temp.isRed() && parent.isRed() && grandParent != null){
                    let uncle = grandParent.left === parent ? grandParent.right : grandParent.left;
                    if(uncle === null || uncle.isBlack()) { // 如果是黑节点
                        let balanceType = this.isBalance(grandParent,parent,temp);
                        // 旋转之后的跟节点
                        let routeRoot = this.balance(balanceType);
                        routeRoot.setBlack();
                        // 把孩子染红
                        if(routeRoot.left != null){
                            routeRoot.left.setRed();
                        }
                        if(routeRoot.right != null){
                            routeRoot.right.setRed();
                        }
                        break;
                    }else if(uncle.isRed()){
                        parent.setBlack();
                        uncle.setBlack();
                        if(grandParent === this.root){
                            grandParent.setBlack();
                        }else{
                            grandParent.setRed();
                        }
                        temp = grandParent;
                        parent = this.searchParent(temp.data);
                        if(parent) {
                            grandParent = this.searchParent(parent.data);
                        }else{
                            grandParent = null;
                        }
                    }
                }
            }
        }
    }

    balance(balance) {
        let type = balance.type;
        let parent = balance.node;
        if ("LL" === type) {
            return this.LL(parent);
        } else if ("RR" === type) {
            return this.RR(parent);
        } else if ("LR" === type) {
            this.RR(parent.left);
            return this.LL(parent);
        } else if ("RL" === type) {
            this.LL(parent.right);
            return this.RR(parent);
        }
    }

    // 判断类型
    isBalance(grandParent,parent,temp) {
        if (temp != null) {
            let first = null;
            let second = null;
            let current = temp;
            if (current != null) {
                second = parent.left === temp ? "L" : "R";
                first = grandParent.left === parent ? "L" : "R";
                let type = first + second;
                let balance = {};
                balance.type = type;
                balance.node = grandParent;
                return balance;
            }
        }
        return null;
    }
}
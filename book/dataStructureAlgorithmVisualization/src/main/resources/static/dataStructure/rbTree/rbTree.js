import Node from "./Node";
import DrawTree from "../common/js/draw/tree/DrawTree";

class CheckResult {
    constructor() {
        this.doubleRedResult = true;
        this.blackHeightResultCount = 0;
        this.blackHeightResult = true;
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

    /**
     * 判断树是否符合要求，验证添加和删除后，是否正确
     */
    isCorrect() {
        let b = true;
        if (this.root) {
            if (!this.root.isBlack()) {
                console.log("根节点不是黑色");
                b = false;
            }
            let result = this.doubleRedAndBlackHeightCheck(this.root);
            if (!result.doubleRedResult) {
                console.log("有相同的红节点");
                b = false;
            }
            if (!result.blackHeightResult) {
                console.log("黑高不相同");
                b = false;
            }
            return b;
        }
        return b;
    }


    doubleRedAndBlackHeightCheck(node) {
        let checkResult = new CheckResult();
        if (node.isBlack()) {
            checkResult.blackHeightResultCount = 1;
        }
        let leftCheck = null;
        let rightCheck = null;
        let leftBlackHeightResultCount = 0;
        let rightBlackHeightResultCount = 0;
        if (node.left) {
            if (node.left.isRed() && node.isRed()) {
                checkResult.doubleRedResult = false;
            }
            leftCheck = this.doubleRedAndBlackHeightCheck(node.left);
            leftBlackHeightResultCount = leftCheck.blackHeightResultCount;
        }
        if (node.right) {
            if (node.right.isRed() && node.isRed()) {
                checkResult.doubleRedResult = false;
            }
            rightCheck = this.doubleRedAndBlackHeightCheck(node.right);
            rightBlackHeightResultCount = rightCheck.blackHeightResultCount;
        }
        if (leftBlackHeightResultCount === rightBlackHeightResultCount) {
            checkResult.blackHeightResultCount += leftBlackHeightResultCount;
            checkResult.blackHeightResult = true;
        }else{
            checkResult.blackHeightResult = false;
        }
        let doubleRedResult = checkResult.doubleRedResult;
        if(leftCheck != null){
            doubleRedResult = doubleRedResult && leftCheck.doubleRedResult;
        }
        if(rightCheck != null){
            doubleRedResult = doubleRedResult && rightCheck.doubleRedResult;
        }
        checkResult.doubleRedResult = doubleRedResult;
        return checkResult;
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
            if (target.left == null && target.right == null && target === this.root) {
                this.root = null;
            } else if (target.left == null && target.right == null && target.isRed()) {
                // 如果是最后一层非空节点且是红色，可以直接删除
                if (parent.left && data.compareTo(parent.left.data) === 0) {
                    parent.left = null;
                } else if (parent.right && data.compareTo(parent.right.data) === 0) {
                    parent.right = null;
                }
                console.log("删除类型1");
            } else if (target.left != null || target.right != null || target.isBlack()) {
                let s = target; // 实际被删除的点
                let r = null; // s的子节点，接替者
                let sData = null;
                let p = null;
                if (target.left != null) {
                    s = this.doSearchMax(target.left);
                    r = s.left;
                } else if (target.right != null) {
                    s = this.doSearchMin(target.right);
                    r = s.right;
                }
                sData = s.data;
                let b = this.getBrother(sData);
                p = this.searchParent(sData);
                if (p.right === s) {
                    p.right = r;
                } else {
                    p.left = r;
                }
                target.data = sData;
                this.xz(s, r, p, b);
            }
        }
    }

    xz(s, r, p, b) {
        let sData = s.data;
        // 实际删除的节点为红色时
        if (s.isRed()) {
            console.log("删除类型2");
        } else if (s.isBlack() && r != null && r.isRed()) {
            r.setBlack();
            console.log("删除类型3");
        } else if (s.isBlack()) {
            // 兄弟节点是黑色且有红孩子
            let redChild = b.left != null && b.left.isRed() ? b.left : b.right != null && b.right.isRed() ? b.right : null;
            if (b.isBlack() && redChild != null) {
                let pColor = p.color; // 先保存原来的颜色
                // 判断 p b 红孩子之间的旋转类型
                let balanceType = this.isBalance(p, b, redChild);
                // 旋转之后的跟节点
                let routeRoot = this.balance(balanceType);
                //因为旋转后，节点变了，需要保持原来的颜色不变
                routeRoot.color = pColor;
                // 把孩子染红
                if (routeRoot.left != null) {
                    routeRoot.left.setBlack();
                }
                if (routeRoot.right != null) {
                    routeRoot.right.setBlack();
                }
                console.log("删除类型4");
            } else if (b.isBlack() && redChild == null && p.isRed()) {
                // b p 直接换色
                let temp = b.color;
                b.color = p.color;
                p.color = temp;
                console.log("删除类型5");
            } else if (b.isBlack() && redChild == null && p.isBlack()) {
                // 直接变色，但要继续修正
                b.setRed();
                let pParent = this.searchParent(p.data);
                if (pParent != null) {
                    let pb = this.getBrother(p.data);
                    let s = new Node();
                    s.setBlack();
                    this.xz(s, null, pParent, pb);
                }
                console.log("删除类型6");
            } else if (b.isRed()) {
                let balanceType = null;
                let newB = null;
                if (b === p.left) {
                    //LL
                    balanceType = this.isBalance(p, b, b.left);
                    this.balance(balanceType);
                    newB = p.left;
                } else {
                    //RR
                    balanceType = this.isBalance(p, b, b.right);
                    this.balance(balanceType);
                    newB = p.right;
                }
                let temp = b.color;
                b.color = p.color;
                p.color = temp;
                let s = new Node();
                s.setBlack();
                this.xz(s, null, p, newB);
                console.log("删除类型7");
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

    getBrother(data) {
        let node = this.search(data);
        if (node != null) {
            let p = this.searchParent(node.data);
            if (p.left === node) {
                return p.right;
            } else if (p.right === node) {
                return p.left;
            }
        }
        return null;
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
                while (temp.isRed() && parent.isRed() && grandParent != null) {
                    let uncle = grandParent.left === parent ? grandParent.right : grandParent.left;
                    if (uncle === null || uncle.isBlack()) { // 如果是黑节点
                        let balanceType = this.isBalance(grandParent, parent, temp);
                        // 旋转之后的跟节点
                        let routeRoot = this.balance(balanceType);
                        routeRoot.setBlack();
                        // 把孩子染红
                        if (routeRoot.left != null) {
                            routeRoot.left.setRed();
                        }
                        if (routeRoot.right != null) {
                            routeRoot.right.setRed();
                        }
                        break;
                    } else if (uncle.isRed()) {
                        parent.setBlack();
                        uncle.setBlack();
                        if (grandParent === this.root) {
                            grandParent.setBlack();
                        } else {
                            grandParent.setRed();
                        }
                        temp = grandParent;
                        parent = this.searchParent(temp.data);
                        if (parent) {
                            grandParent = this.searchParent(parent.data);
                        } else {
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
    isBalance(grandParent, parent, temp) {
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

export default RBTree;
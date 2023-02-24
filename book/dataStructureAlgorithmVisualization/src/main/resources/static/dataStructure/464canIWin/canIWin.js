import Node from './Node';
import DrawTree from "../common/js/draw/tree/DrawTree";

class CanIWin {
    constructor() {
        this.root = new Node();
    }

    canIWin(maxChoosableInteger, desiredTotal) {
        this.root = new Node();
        let node = this.root;
        const dfs = (maxChoosableInteger, usedNumbers, desiredTotal, currentTotal, node, x) => {
            if(x >= 9){
                throw "数据量过大！";
            }
            let res = false;
            for (let i = 0; i < maxChoosableInteger; i++) {
                if (((usedNumbers >> i) & 1) === 0) {
                    if (i + 1 + currentTotal >= desiredTotal) {
                        res = true;
                    }
                    let cNode = new Node();
                    let id = i + 1;
                    cNode.id = id;
                    if (!res && !dfs(maxChoosableInteger, usedNumbers | (1 << i), desiredTotal, currentTotal + i + 1, cNode, x + 1)) {
                        res = true;
                    }
                    if (x % 2 === 1) {
                        cNode.color = "red";
                        if (res) {
                            node.children.push(cNode);
                        }
                    } else {
                        if (!res) {
                            node.children.push(cNode);
                        }
                    }
                    if (res === true) {
                        break;
                    }
                }
            }
            return res;
        }
        // 如果所有数字都选完还是小于要选的总数，则直接返回false
        if ((1 + maxChoosableInteger) * (maxChoosableInteger) / 2 < desiredTotal) {
            return false;
        }
        let r = dfs(maxChoosableInteger, 0, desiredTotal, 0, node, 1);
        this.root = this.root.children[0];
        return r;
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

export default CanIWin;
import Node from './Node';

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

class Heap {
    constructor(min) {
        this.arr = [];
        this.length = 0;
        this.min = min;
    }

// 优化画图逻辑，从最后一层开始画，节点不会再重叠在一起
    drawTreeOptimize(ctx, startX, startY, startSplitY) {
        ctx.clearRect(0, 0, 10000000, 1000000);
        if (!this.isEmpty()) {
            this.drawNodeOptimize(ctx, 0, startX, startY, startSplitY);
        }
    }

    drawNodeOptimize(ctx, index, x, y, splitY) {
        let left = index * 2 + 1;
        let right = index * 2 + 2;
        let childrenList = [];
        if (left < this.length) {
            childrenList.add(left);
        }
        if (right < this.length) {
            childrenList.add(right);
        }

        let length = childrenList.length;
        let statusList = [];
        let allWidth = 0;
        let splitX = 30;
        let splitXFinal = splitX;
        for (let i = 0; i < length; i++) {
            let childIndex = childrenList[i];
            let pre = null;
            let xx = x;
            let width = 0;
            if (i - 1 >= 0) {
                pre = statusList.get(i - 1);
                xx = pre.getX();
                width = pre.getWidth();
                splitX = splitXFinal;
            } else {
                splitX = 0;
            }
            let s = this.drawNodeOptimize(ctx, childIndex, xx + width * 2 + splitX, y + splitY, splitY);
            statusList.add(s);
        }

        let node = this.arr[index];

        let nodeWidth = node.data.toString().length * 6;
        nodeWidth = Math.max(nodeWidth, 15);
        let height = 15;
        let currentX = x + nodeWidth;
        if (statusList.size() > 0) {
            let first = statusList.get(0);
            let last = statusList.get(statusList.size() - 1);
            if (first.getNode() === last.getNode()) {
                currentX = first.getMiddle() + splitXFinal / 2;
            } else {
                // 有两个以上结点时
                currentX = (first.getMiddle() + last.getMiddle()) / 2;
            }
            let currentY = y + height;
            ctx.restore();
            for (let i = 0; i < statusList.size(); i++) {
                let s = statusList.get(i);
                ctx.beginPath();
                ctx.moveTo(currentX, currentY);
                ctx.lineTo(s.getMiddle(), s.getY() - height);
                ctx.stroke();
                allWidth += s.getWidth() + splitX / 2;
            }
            allWidth -= splitX / 2;
        }
        ctx.save();
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
        let status = new Status();
        status.setY(y);
        status.setMiddle(currentX);
        status.setNode(node);
        if (statusList.size() === 0) {
            status.setWidth(nodeWidth);
            status.setX(x);
        } else {
            status.setWidth(allWidth);
            status.setX(statusList[0].getX());
        }
        return status;
    }

    push(value) {
        let node = new Node(value);
        this.arr[this.length] = node;
        this.up(this.length);
        this.length++;
    }

    pop() {
        if (this.length > 0) {
            let result = this.arr[0];
            this.swap(0, --this.length);
            this.down(0);
            return result.data;
        }
        return null;
    }

    isEmpty() {
        return this.length <= 0;
    }

    up(i) {
        let parentIndex = 0;
        while ((parentIndex = Math.floor((i - 1) / 2)) >= 0 && i > 0) {
            let b = this.arr[i].data < this.arr[parentIndex].data;
            if (this.min && b) {
                this.swap(i, parentIndex);
            }
            if (!this.min && !b) {
                this.swap(i, parentIndex);
            }
            i = parentIndex;
        }
    }

    down(i) {
        while (i * 2 + 1 < this.length) {
            let left = i * 2 + 1;
            let right = i * 2 + 2;
            if (this.min) {
                let minIndex = left;
                if (right < this.length) {
                    if (this.arr[right].data < this.arr[minIndex].data) {
                        minIndex = right;
                    }
                }
                if (this.arr[i].data > this.arr[minIndex].data) {
                    this.swap(i, minIndex);
                    i = minIndex;
                } else {
                    break;
                }
            } else {
                let maxIndex = left;
                if (right < this.length) {
                    if (this.arr[right].data > this.arr[maxIndex].data) {
                        maxIndex = right;
                    }
                }
                if (this.arr[i].data < this.arr[maxIndex].data) {
                    this.swap(i, maxIndex);
                    i = maxIndex;
                } else {
                    break;
                }
            }
        }
    }

    getHeight() {
        return Math.ceil(Math.sqrt(this.length + 1));
    }

    swap(i, j) {
        let temp = this.arr[i];
        this.arr[i] = this.arr[j];
        this.arr[j] = temp;
    }
}

export default Heap;
class Status {
    setX(x) {
        this.x = x;
    }

    getX() {
        return this.x;
    }

    setMiddle(middle){
        this.middle = middle;
    }

    getMiddle(){
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

    setNode(node){
        this.node = node;
    }

    getNode(){
        return this.node;
    }
}

class DrawTree {
    constructor() {
    }

    static drawNodeOptimize(ctx, node, x, y, splitY) {
        let length = node.getChildrenList().length;
        let statusList = [];
        let allWidth = 0;
        let splitX = 30;
        let splitXFinal = splitX;
        for (let i = 0; i < length; i++) {
            let childNode = node.getChildrenList()[i];
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
            let s = DrawTree.drawNodeOptimize(ctx, childNode, xx + width * 2 + splitX, y + splitY, splitY);
            statusList.add(s);
        }

        let nodeWidth = node.getDataList().toString().length * 6;
        nodeWidth = Math.max(nodeWidth, 15);
        let height = 15;
        let currentX = x + nodeWidth;
        if (statusList.size() > 0) {
            let first = statusList.get(0);
            let last = statusList.get(statusList.size() - 1);
            if (first.getNode() === last.getNode()) {
                // 只有一个结点时
                if (first.getNode() === node.right) {
                    currentX = first.getMiddle() - splitXFinal / 2;
                }else{
                    currentX = first.getMiddle() + splitXFinal / 2;
                }
            } else {
                // 有两个以上结点时
                currentX = (first.getMiddle() + last.getMiddle()) / 2;
            }
            let currentY = y + height;
            ctx.restore();
            for (let i = 0; i < statusList.size(); i++) {
                let s = statusList.get(i);
                ctx.beginPath();
                let color = s.getNode().color;
                if (color) {
                    ctx.strokeStyle = color;
                } else {
                    ctx.strokeStyle = "black";
                }
                ctx.moveTo(currentX, currentY);
                ctx.lineTo(s.getMiddle(), s.getY() - height);
                ctx.stroke();
                allWidth += s.getWidth() + splitX / 2;
            }
            allWidth -= splitX / 2;
        }

        node.x = currentX;
        node.y = y;
        node.width = nodeWidth;
        node.height = height;

        ctx.save();
        ctx.beginPath();
        ctx.ellipse(currentX, y, nodeWidth, height, 0, 0, 2 * Math.PI);
        ctx.font = "15px 微软雅黑";
        if (node.color) {
            ctx.strokeStyle = node.color;
        } else {
            ctx.strokeStyle = "black";
        }
        let sub = node.getDataList().toString().length * 5;
        ctx.strokeText(node.getDataList().toString(), currentX - sub, y + 6);
        ctx.stroke();

        if (node.getPre && node.getPre()) {
            ctx.save();
            ctx.strokeStyle = "red";
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
            ctx.restore();
        }

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
}

export default DrawTree;
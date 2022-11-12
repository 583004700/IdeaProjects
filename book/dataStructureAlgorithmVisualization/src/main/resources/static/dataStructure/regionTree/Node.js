class Node {

    // color,data,left,right

    constructor() {
        // 因为默认是黑色的，先初始化为非红非黑，可以保证判断出所有节点都被赋过红或黑
        this.color = "black";
        this.data = null;
        this.startIndex = null;
        this.endIndex = null;
        this.cacheNumber = 0;
        this.left = null;
        this.right = null;
    }

    setRed() {
        this.color = "red";
    }

    setBlack() {
        this.color = "black";
    }

    isRed() {
        return this.color === "red";
    }

    isBlack() {
        return this.color === "black";
    }

    getChildrenList() {
        let childrenList = [];
        if (this.left != null) {
            childrenList.add(this.left);
        }
        if (this.right != null) {
            childrenList.add(this.right);
        }
        return childrenList;
    }

    getDataList() {
        let fh = this.cacheNumber >= 0 ? "+" : "-";
        let splitChar = "\t";
        return this.data + splitChar + "[" + this.startIndex + "," + this.endIndex + "]"
            + splitChar + fh + Math.abs(this.cacheNumber);
    }
}

export default Node;
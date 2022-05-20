class Node {

    // color,data,left,right

    setRed(){
        this.color = "red";
    }

    setBlack(){
        this.color = "black";
    }

    getChildrenList(){
        let childrenList = [];
        if (this.left != null) {
            childrenList.add(this.left);
        }
        if (this.right != null) {
            childrenList.add(this.right);
        }
        return childrenList;
    }

    getDataList(){
        return this.data;
    }
}

export default Node;
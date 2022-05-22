class Node {

    // color,data,left,right

    constructor(data,weight) {
        // 因为默认是黑色的，先初始化为非红非黑，可以保证判断出所有节点都被赋过红或黑
        this.color = "gray";
        this.data = data;
        // 权重
        this.weight = weight;
        this.left = null;
        this.right = null;
    }

    isValid(){
        return this.color !== "gray";
    }

    setWeight(weight){
        this.weight = weight;
    }

    setLeft(left){
        this.left = left;
    }

    setRight(right){
        this.right = right;
    }

    setGray(){
        this.color = "gray";
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
        return this.data+":"+this.weight;
    }
}

export default Node;
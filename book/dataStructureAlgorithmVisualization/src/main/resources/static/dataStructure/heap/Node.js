class Node {

    // color,data,left,right

    setRed(){
        this.color = "red";
    }

    setBlack(){
        this.color = "black";
    }

    constructor(data) {
        this.data = data;
    }
}

export default Node;
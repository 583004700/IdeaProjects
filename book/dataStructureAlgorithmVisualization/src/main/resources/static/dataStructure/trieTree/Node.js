class Node {
    constructor() {
        this.ch = null;
        this.isTail = false;
        this.children = new Map();
        this.color = "black";
    }

    setIsTail(isTail){
        this.isTail = isTail;
        if(this.isTail === true){
            this.color = "red";
        }
    }

    getDataList() {
        return [this.ch];
    }

    getChildrenList() {
        let ele = this.children.values();
        return [...ele];
    }

    toString() {
        return this.ch.toString();
    }

    compareTo(o) {
        return this.getDataList().get(0).compareTo(o.getDataList().get(0));
    }
}

export default Node;
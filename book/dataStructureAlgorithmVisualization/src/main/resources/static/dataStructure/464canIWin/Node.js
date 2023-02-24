class Node {
    constructor() {
        this.id = null;
        this.children = [];
        this.color = "black";
    }

    getDataList() {
        return [this.id];
    }

    getChildrenList() {
        return this.children;
    }

    toString() {
        return this.id.toString();
    }

    compareTo(o) {
        return this.getDataList().get(0).compareTo(o.getDataList().get(0));
    }
}

export default Node;
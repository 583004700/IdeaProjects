class Up {

    constructor() {
        this.list = [];
    }

    setValue(value) {
        this.value = value;
    }

    getValue() {
        return this.value;
    }

    setList(list) {
        this.list = list;
    }

    getList() {
        return this.list;
    }
}

class Node {

    constructor(order) {
        this.order = order;
        this.dataList = [];
        this.childrenList = [];
    }

    setDataList(dataList) {
        this.dataList = dataList;
    }

    getDataList() {
        return this.dataList;
    }

    setChildrenList(childrenList) {
        this.childrenList = childrenList;
    }

    getChildrenList() {
        return this.childrenList;
    }

    static getInstance(data, order) {
        let node = new Node(order);
        node.getDataList().add(data);
        return node;
    }

    toString() {
        return this.dataList.toString() + "  |  ";
    }

    getMin() {
        if (this.dataList.length > 0) {
            return this.dataList.get(0);
        }
        return null;
    }

    getMax() {
        if (this.dataList.length > 0) {
            return this.dataList.get(this.dataList.length - 1);
        }
        return null;
    }

    getLeftNode(data) {
        if (data || data === 0) {
            let i = this.searchIndex(data);
            if (i >= 0 && i < this.childrenList.length) {
                return this.childrenList.get(i);
            }
            return null;
        } else {
            let min = this.getMin();
            if (min != null) {
                return this.getLeftNode(min);
            }
            return null;
        }
    }

    getRightNode(data) {
        if (data || data === 0) {
            let i = this.searchIndex(data) + 1;
            if (i >= 0 && i < this.childrenList.length) {
                return this.childrenList.get(i);
            }
            return null;
        } else {
            let max = this.getMax();
            if (max != null) {
                return this.getRightNode(max);
            }
            return null;
        }
    }

    searchIndex(data) {
        return this.doSearchIndex(data, 0, this.dataList.length - 1);
    }

    add(data) {
        let i = this.searchInsertIndex(data);
        this.dataList.add(data, i);
        if (this.dataList.length >= this.order) {
            let up = new Up();
            let middle = (this.dataList.length - 1) / 2;
            middle = Math.floor(middle);
            let middleData = this.getDataList().get(middle);
            up.setValue(middleData);
            let i1 = this.searchNodeInsertIndex(middleData);
            let l1 = new Node(this.order);
            let l2 = new Node(this.order);
            if (i1 !== -1) {
                for (let j = 0; j < i1; j++) {
                    l1.addChildNode(this.childrenList.get(j));
                }
                for (let j = i1; j < this.childrenList.length; j++) {
                    l2.addChildNode(this.childrenList.get(j));
                }
            }

            let k1 = this.searchIndex(middleData);
            for (let j = 0; j < k1; j++) {
                l1.add(this.dataList.get(j));
            }
            for (let j = k1 + 1; j < this.dataList.length; j++) {
                l2.add(this.dataList.get(j));
            }
            up.getList().add(l1);
            up.getList().add(l2);
            return up;
        }
        return null;
    }

    removeChildNode(node) {
        this.childrenList.remove(node);
    }

    removeData(data) {
        let index = this.searchIndex(data);
        if (index !== -1) {
            return this.getDataList().removeIndex(index);
        }
        return null;
    }

    addChildNode(node) {
        let i = this.searchNodeInsertIndex(node.getDataList().get(0));
        this.childrenList.add(node, i);
    }

    searchNodeInsertIndex(data) {
        let node = new Node(this.order);
        node.getDataList().add(data);
        node.getChildrenList().add(node);
        return this.doSearchNodeInsertIndex(node, 0, this.childrenList.length - 1);
    }

    doSearchNodeInsertIndex(data, startIndex, endIndex) {
        if (startIndex > endIndex) {
            return startIndex;
        }
        let middle = Math.floor((startIndex + endIndex) / 2);
        let middleData = this.childrenList.get(middle);
        if (data.compareTo(middleData) < 0) {
            return this.doSearchNodeInsertIndex(data, startIndex, middle - 1);
        } else if (data.compareTo(middleData) > 0) {
            return this.doSearchNodeInsertIndex(data, middle + 1, endIndex);
        } else {
            return middle;
        }
    }

    searchInsertIndex(data) {
        return this.doSearchInsertIndex(data, 0, this.dataList.length - 1);
    }

    searchNodeIndex(node) {
        return this.doSearchNodeIndex(node, 0, this.childrenList.length - 1);
    }

    doSearchNodeIndex(node, startIndex, endIndex) {
        if (startIndex > endIndex) {
            return -1;
        }
        let middle = (startIndex + endIndex) / 2;
        middle = Math.floor(middle);
        let middleData = this.childrenList.get(middle);
        if (node.compareTo(middleData) < 0) {
            return this.doSearchNodeIndex(node, startIndex, middle - 1);
        } else if (node.compareTo(middleData) > 0) {
            return this.doSearchNodeIndex(node, middle + 1, endIndex);
        } else {
            return middle;
        }
    }

    doSearchIndex(data, startIndex, endIndex) {
        if (startIndex > endIndex) {
            return -1;
        }
        let middle = (startIndex + endIndex) / 2;
        middle = Math.floor(middle);
        let middleData = this.dataList.get(middle);
        if (data.compareTo(middleData) < 0) {
            return this.doSearchIndex(data, startIndex, middle - 1);
        } else if (data.compareTo(middleData) > 0) {
            return this.doSearchIndex(data, middle + 1, endIndex);
        } else {
            return middle;
        }
    }

    doSearchInsertIndex(data, startIndex, endIndex) {
        if (startIndex > endIndex) {
            return startIndex;
        }
        let middle = (startIndex + endIndex) / 2;
        middle = Math.floor(middle);
        let middleData = this.dataList.get(middle);
        if (data.compareTo(middleData) < 0) {
            return this.doSearchInsertIndex(data, startIndex, middle - 1);
        } else if (data.compareTo(middleData) > 0) {
            return this.doSearchInsertIndex(data, middle + 1, endIndex);
        } else {
            return middle;
        }
    }

    compareTo(o) {
        return this.getDataList().get(0).compareTo(o.getDataList().get(0));
    }
}

export default Node;
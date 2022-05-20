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
        this.next = null;
        this.pre = null;
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

    setNext(next) {
        this.next = next;
        if (next != null) {
            next.pre = this;
        }
    }

    setPre(pre) {
        this.pre = pre;
    }

    getPre() {
        return this.pre;
    }

    getNext() {
        return this.next;
    }

    static getInstance(data, order) {
        let node = new Node(order);
        node.getDataList().add(data);
        return node;
    }

    toString() {
        return this.dataList.toString() + "  |  ";
    }

    getScope(min, max) {
        let result = [];
        let start = this;
        w:
            while (start != null && start.dataList.size() > 0) {
                for (let i = 0; i < start.dataList.size(); i++) {
                    let t = start.dataList.get(i);
                    if (t.compareTo(min) >= 0 && t.compareTo(max) <= 0) {
                        result.add(t);
                    }
                    if (t.compareTo(max) > 0) {
                        break w;
                    }
                }
                start = start.next;
            }
        return result;
    }

    getMin() {
        if (this.dataList.size() > 0) {
            return this.dataList.get(0);
        }
        return null;
    }

    getMax() {
        if (this.dataList.size() > 0) {
            return this.dataList.get(this.dataList.size() - 1);
        }
        return null;
    }

    getLeftNode(data) {
        if (data || data === 0) {
            let i = this.searchIndex(data);
            if (i >= 0 && i < this.childrenList.size()) {
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
        let i = this.searchIndex(data) + 1;
        if (i >= 0 && i < this.childrenList.size()) {
            return this.childrenList.get(i);
        }
        return null;
    }

    searchIndex(data) {
        return this.doSearchIndex(data, 0, this.dataList.size() - 1);
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

    removeDataAndChildrenNode(data) {
        let i = this.searchIndex(data);
        this.getDataList().removeIndex(i);
        this.getChildrenList().removeIndex(i);
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

    add(data) {
        let i = this.searchInsertIndex(data);
        this.dataList.add(data, i);
        let up = null;
        if (this.dataList.size() > this.order) {
            up = new Up();
            let middle = (this.dataList.size() - 1) / 2;
            middle = Math.floor(middle);
            let middleData = this.getDataList().get(middle);
            up.setValue(middleData);
            let i1 = this.searchNodeInsertIndex(middleData);
            let l1 = new Node(this.order);
            let l2 = new Node(this.order);
            if (i1 !== -1) {
                for (let j = 0; j < i1 && j < this.childrenList.size(); j++) {
                    l1.addChildNode(this.childrenList.get(j));
                }
                for (let j = i1; j < this.childrenList.size(); j++) {
                    l2.addChildNode(this.childrenList.get(j));
                }
            }

            let k1 = this.searchIndex(middleData);
            for (let j = 0; j <= k1 && j < this.dataList.size(); j++) {
                l1.add(this.dataList.get(j));
            }
            for (let j = k1 + 1; j < this.dataList.size(); j++) {
                l2.add(this.dataList.get(j));
            }
            up.getList().add(l1);
            up.getList().add(l2);
            return up;
        }
        return null;
    }

    getRightNode() {
        let max = this.getMax();
        if (max != null) {
            return this.getLeftNode(max);
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
        return this.doSearchNodeInsertIndex(node, 0, this.childrenList.size() - 1);
    }

    doSearchNodeInsertIndex(data, startIndex, endIndex) {
        if (startIndex > endIndex) {
            return startIndex;
        }
        let middle = (startIndex + endIndex) / 2;
        middle = Math.floor(middle);
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
        return this.doSearchInsertIndex(data, 0, this.dataList.size() - 1);
    }

    searchNodeIndex(node, merge) {
        if (merge) {
            return this.doSearchNodeIndex(node, 0, childrenList.size() - 1);
        } else {
            return this.doSearchNodeIndexByLine(node);
        }
    }

    doSearchNodeIndexByLine(node) {
        for (let i = 0; i < this.getChildrenList().size(); i++) {
            if (this.getChildrenList().get(i) === node) {
                return i;
            }
        }
        return -1;
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
}

export default Node;
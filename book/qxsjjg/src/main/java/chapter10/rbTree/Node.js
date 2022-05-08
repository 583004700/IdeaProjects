Array.prototype.remove = function (data) {
    this.forEach(function (item, index, arr) {
        if (item === data) {
            arr.splice(index, 1);
        }
    });
}

Array.prototype.removeIndex = function (index) {
    this.splice(index, 1);
}

Array.prototype.add = function (data, index) {
    if (index || index === 0) {
        this.splice(index, 0, data);
    } else {
        this.push(data);
    }
}

Array.prototype.get = function (index) {
    return this[index];
}

Array.prototype.contains = function (data) {
    for (let i = 0; i < this.length; i++) {
        if (this[i] === data) {
            return true;
        }
    }
    return false;
}

Array.prototype.addAll = function (data) {
    for (let i = 0; i < data.length; i++) {
        this.push(data[i]);
    }
}

Array.prototype.size = function () {
    return this.length;
}

Array.prototype.contains = function (data) {
    for (let i = 0; i < this.length; i++) {
        let t = this[i];
        if (t === data) {
            return true;
        }
    }
    return false;
}

Array.prototype.set = function (index, data) {
    this[index] = data;
}

Object.prototype.get = function (key) {
    return this[key];
}

Object.prototype.put = function (key, value) {
    this[key] = value;
}

Object.prototype.containsKey = function (key) {
    return typeof this.get(key) === "undefined";
}

class Node {

    // color,data,left,right

    constructor() {
        // 因为默认是黑色的，先初始化为非红非黑，可以保证判断出所有节点都被赋过红或黑
        this.color = "green";
        this.data = null;
        this.left = null;
        this.right = null;
    }

    setRed(){
        this.color = "red";
    }

    setBlack(){
        this.color = "black";
    }

    isRed(){
        return this.color === "red";
    }

    isBlack(){
        return this.color === "black";
    }
}
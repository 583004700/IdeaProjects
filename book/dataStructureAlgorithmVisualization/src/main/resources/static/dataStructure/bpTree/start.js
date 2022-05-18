import '../common/css/header.css';
import BPTree from "./BPTree";

let order = 4;
let bsTree = null;
let canvasEle = document.getElementById("canvas");
let ctx = canvasEle.getContext("2d");
let orderValueEle = document.getElementById("orderValue");
let addValueEle = document.getElementById("addValue");
let deleteValueEle = document.getElementById("deleteValue");
let splitY = 60;
canvasEle.width = 10000;

window.onload = function () {
    setTimeout(function () {
        window.scrollTo({"top": 0, "left": 0, "behavior": 'smooth'});
    }, 1000);
}

window.isNumber = function (n) {
    return /^\d+\.?\d*$/.test(n);
}

window.isInt = function (n) {
    return /^\d+?\d*$/.test(n);
}

window.validate = function () {
    let orderValue = orderValueEle.value;
    if (!isInt(orderValue) || parseInt(orderValue) < 3) {
        alert("阶数设置不正确!");
        return false;
    }
    return true;
}

window.changeOrder = function () {
    let orderValue = orderValueEle.value;
    if (!validate()) {
        return;
    }
    orderValue = parseInt(orderValue);
    orderValueEle.value = orderValue;
    order = orderValue;
    alert("阶数设置成功！");
    reset();
}

window.reset = function () {
    bsTree = new BPTree(order);
    bsTree.drawTreeOptimize(ctx);
}

let startX = 50;
let startY = 100;

window.addV = function () {
    if (!bsTree) {
        bsTree = new BPTree(order);
    }
    if (!validate()) {
        return;
    }
    let addValue = addValueEle.value;
    if (!isNumber(addValue)) {
        alert("请输入数字!");
        return;
    }
    addValue = parseFloat(addValue);
    if (bsTree.search(addValue) != null) {
        alert("节点已经存在！");
        return;
    }
    bsTree.add(addValue);
    let treeHeight = bsTree.getHeight();
    canvasEle.height = treeHeight * splitY + window.innerHeight;
    bsTree.drawTreeOptimize(ctx, startX, startY, splitY);
    addValueEle.value = "";
}

window.deleteV = function () {
    if (!bsTree) {
        bsTree = new BPTree(order);
    }
    if (!validate()) {
        return;
    }
    let deleteValue = deleteValueEle.value;
    if (!isNumber(deleteValue)) {
        alert("请输入数字!");
        return;
    }
    deleteValue = parseFloat(deleteValue);
    if (bsTree.search(deleteValue) == null) {
        alert("节点不存在！");
        return;
    }
    bsTree.delete(deleteValue);
    let treeHeight = bsTree.getHeight();
    canvasEle.height = treeHeight * splitY + window.innerHeight;
    bsTree.drawTreeOptimize(ctx, startX, startY, splitY);
    deleteValueEle.value = "";
}
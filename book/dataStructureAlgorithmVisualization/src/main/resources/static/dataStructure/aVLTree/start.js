import '../common/css/header.css';
import AVLTree from "./AVLTree";

let bsTree = new AVLTree();
let canvasEle = document.getElementById("canvas");
let ctx = canvasEle.getContext("2d");
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

let startX = 50;
let startY = 100;

window.addV = function () {
    let addValue = addValueEle.value;
    if (!isNumber(addValue)) {
        alert("请输入数字!");
        return;
    }
    addValue = parseFloat(addValue);
    if (bsTree.search(addValue) != null) {
        alert("数据已经存在！");
        return;
    }
    bsTree.add(addValue);
    let treeHeight = bsTree.getHeight();
    canvasEle.height = treeHeight * splitY + window.innerHeight;
    bsTree.drawTreeOptimize(ctx, startX, startY, splitY);
    addValueEle.value = "";
}

window.deleteV = function () {
    let deleteValue = deleteValueEle.value;
    if (!isNumber(deleteValue)) {
        alert("请输入数字!");
        return;
    }
    deleteValue = parseFloat(deleteValue);
    if (bsTree.search(deleteValue) == null) {
        alert("数据不存在！");
        return;
    }
    bsTree.delete(deleteValue);
    let treeHeight = bsTree.getHeight();
    canvasEle.height = treeHeight * splitY + window.innerHeight;
    bsTree.drawTreeOptimize(ctx, startX, startY, splitY);
    deleteValueEle.value = "";
}
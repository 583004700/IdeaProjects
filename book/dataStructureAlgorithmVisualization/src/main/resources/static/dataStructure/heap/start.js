import '../common/css/header.css';
import Heap from "./heap";
import ArrayExtend from "../common/js/extends/ArrayExtend";
import NumberExtend from "../common/js/extends/NumberExtend";
import ObjectExtend from "../common/js/extends/ObjectExtend";

ArrayExtend.exec();
NumberExtend.exec();
ObjectExtend.exec();

let heap = new Heap(true);
let canvasEle = document.getElementById("canvas");
canvasEle.width = window.innerWidth;
canvasEle.height = window.innerHeight * 4;
let ctx = canvasEle.getContext("2d");
let addValueEle = document.getElementById("addValue");
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
    heap.push(addValue);
    let treeHeight = heap.getHeight();
    canvasEle.height = treeHeight * splitY + window.innerHeight;
    heap.drawTreeOptimize(ctx, startX, startY, splitY);
    addValueEle.value = "";
}

window.deleteV = function () {
    heap.pop();
    let treeHeight = heap.getHeight();
    canvasEle.height = treeHeight * splitY + window.innerHeight;
    heap.drawTreeOptimize(ctx, startX, startY, splitY);
}
import '../../common/css/header.css';
import RegionTree from "./regionTree";
import ArrayExtend from "../../common/js/extends/ArrayExtend";
import NumberExtend from "../../common/js/extends/NumberExtend";
import ObjectExtend from "../../common/js/extends/ObjectExtend";

ArrayExtend.exec();
NumberExtend.exec();
ObjectExtend.exec();

let regionTree = new RegionTree();
let canvasEle = document.getElementById("canvas");
canvasEle.width = window.innerWidth;
canvasEle.height = window.innerHeight * 4;
let ctx = canvasEle.getContext("2d");
let numsArrEle = document.getElementById("numsArr");
let opeEle = document.getElementById("ope");
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

window.build = function () {
    let arr = JSON.parse(numsArrEle.value);
    let opt = opeEle.value;
    regionTree.build(arr,opt);
    let treeHeight = regionTree.getHeight();
    canvasEle.height = treeHeight * splitY + window.innerHeight;
    regionTree.drawTreeOptimize(ctx, startX, startY, splitY);
}
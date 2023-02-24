import '../../common/css/header.css';
import CanIWin from "./canIWin";
import ArrayExtend from "../../common/js/extends/ArrayExtend";
import NumberExtend from "../../common/js/extends/NumberExtend";
import ObjectExtend from "../../common/js/extends/ObjectExtend";

ArrayExtend.exec();
NumberExtend.exec();
ObjectExtend.exec();

let tree = null;
let canvasEle = document.getElementById("canvas");
let ctx = canvasEle.getContext("2d");
let maxChoosableIntegerEle = document.getElementById("maxChoosableInteger");
let desiredTotalEle = document.getElementById("desiredTotal");
let splitY = 60;
canvasEle.width = 10000;

window.onload = function () {
    setTimeout(function () {
        window.scrollTo({"top": 0, "left": 0, "behavior": 'smooth'});
    }, 1000);
}

window.isInt = function (n) {
    return /^\d+?\d*$/.test(n);
}

window.reset = function () {
    tree = new CanIWin();
    tree.drawTreeOptimize(ctx);
}

let startX = 50;
let startY = 100;

window.execCanIWin = function () {
    if (!tree) {
        tree = new CanIWin();
    }
    let maxChoosableIntegerValue = maxChoosableIntegerEle.value;
    let desiredTotalValue = desiredTotalEle.value;
    if (!isInt(maxChoosableIntegerValue)) {
        alert("maxChoosableInteger只能是整数！");
        return;
    }
    if (!isInt(desiredTotalValue)) {
        alert("desiredTotal只能是整数！");
        return;
    }
    let b = null;
    try {
        b = tree.canIWin(parseInt(maxChoosableIntegerValue), parseInt(desiredTotalValue));
    }catch (e){
        alert(e);
        return;
    }
    if(b) {
        let treeHeight = tree.getHeight();
        canvasEle.height = treeHeight * splitY + window.innerHeight;
        tree.drawTreeOptimize(ctx, startX, startY, splitY);
    }else{
        alert("不能稳赢！");
    }
}
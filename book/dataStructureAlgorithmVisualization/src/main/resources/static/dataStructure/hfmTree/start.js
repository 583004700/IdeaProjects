import '../../common/css/header.css';
import HfmTree from "./hfmTree";
import ArrayExtend from "../../common/js/extends/ArrayExtend";
import NumberExtend from "../../common/js/extends/NumberExtend";
import ObjectExtend from "../../common/js/extends/ObjectExtend";

ArrayExtend.exec();
NumberExtend.exec();
ObjectExtend.exec();

let bsTree = new HfmTree();
let canvasEle = document.getElementById("canvas");
canvasEle.width = window.innerWidth;
canvasEle.height = window.innerHeight * 4;
let ctx = canvasEle.getContext("2d");
let addValueEle = document.getElementById("addValue");
let weightValueEle = document.getElementById("weightValue");
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
    let weightValue = weightValueEle.value;
    if (!isNumber(addValue)) {
        alert("请输入数据，类型为数字!");
        return;
    }
    if(!isNumber(weightValue)){
        alert("请输入权重，类型为数字！");
        return;
    }
    addValue = parseFloat(addValue);
    weightValue = parseFloat(weightValue);
    if (bsTree.search(addValue) != null) {
        alert("数据已经存在！");
        return;
    }
    bsTree.add(addValue,weightValue);
    let treeHeight = bsTree.getHeight();
    canvasEle.height = treeHeight * splitY + window.innerHeight;
    bsTree.drawTreeOptimize(ctx, startX, startY, splitY);
    addValueEle.value = "";
    weightValueEle.value="";
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

window.getCodesV = function(){
    let codes = bsTree.getCodes();
    alert("编码为：\n"+codes);
}
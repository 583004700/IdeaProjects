import '../../common/css/header.css';
import TrieTree from "./trieTree";
import ArrayExtend from "../../common/js/extends/ArrayExtend";
import NumberExtend from "../../common/js/extends/NumberExtend";
import ObjectExtend from "../../common/js/extends/ObjectExtend";

ArrayExtend.exec();
NumberExtend.exec();
ObjectExtend.exec();

let trieTree = null;
let canvasEle = document.getElementById("canvas");
let ctx = canvasEle.getContext("2d");
let addValueEle = document.getElementById("addValue");
let splitY = 60;
canvasEle.width = 10000;

window.onload = function () {
    setTimeout(function () {
        window.scrollTo({"top": 0, "left": 0, "behavior": 'smooth'});
    }, 1000);
}

window.reset = function () {
    trieTree = new TrieTree();
    trieTree.drawTreeOptimize(ctx);
}

let startX = 50;
let startY = 100;

window.addV = function () {
    if (!trieTree) {
        trieTree = new TrieTree();
    }
    let addValue = addValueEle.value;
    if (addValue.trim() === "") {
        alert("不能为空！");
        return;
    }
    if (trieTree.search(addValue) === true) {
        alert("单词已经存在！");
        return;
    }
    trieTree.insert(addValue);
    let treeHeight = trieTree.getHeight();
    canvasEle.height = treeHeight * splitY + window.innerHeight;
    trieTree.drawTreeOptimize(ctx, startX, startY, splitY);
    addValueEle.value = "";
}
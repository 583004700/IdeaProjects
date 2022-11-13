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
let startIndexEle = document.getElementById("startIndex");
let endIndexEle = document.getElementById("endIndex");
let regionValueEle = document.getElementById("regionValue");

let uStartIndexEle = document.getElementById("uStartIndex");
let uEndIndexEle = document.getElementById("uEndIndex");
let uValueEle = document.getElementById("uValue");
let regionsEle = document.getElementById("regions");
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

let startX = 50;
let startY = 100;

window.build = function () {
    let arr = JSON.parse(numsArrEle.value);
    preArr = arr;
    current = arr;
    let opt = opeEle.value;
    regionTree.build(arr, opt);
    let treeHeight = regionTree.getHeight();
    canvasEle.height = treeHeight * splitY + window.innerHeight;
    regionTree.drawTreeOptimize(ctx, startX, startY, splitY);
}

function randomRange(min, max) { // min最小值，max最大值
    return Math.floor(Math.random() * (max - min)) + min;
}

window.test = function () {
    alert("请打开控制台查看结果！");
    let arr = [];
    let q1 = 1;
    let q2 = 10000000;
    let len = 10000000;
    let middle = Math.floor(len / 2);
    for (let i = 0; i < len; i++) {
        arr.push(randomRange(q1, q2));
    }
    regionTree = new RegionTree();
    regionTree.build(arr, '区间和');
    setInterval(function () {
        let startIndex = randomRange(q1, middle / 2);
        let endIndex = randomRange(middle, len);

        let updateValue = randomRange(-10, 10);

        // 更新区间值
        let startT = new Date().getTime();
        updateRegionFor(startIndex, endIndex, arr, updateValue);
        let endT = new Date().getTime();
        regionTree.update(startIndex, endIndex, updateValue);
        let endT2 = new Date().getTime();
        console.log("更新区间 " + startIndex + " 至 " + endIndex + " 的值!，for循环耗时：" + (endT - startT));
        console.log("更新区间 " + startIndex + " 至 " + endIndex + " 的值!，线段树耗时：" + (endT2 - endT));
        // 重新计算区间值
        let startTime1 = new Date().getTime();
        let r1 = getRegionFor(startIndex, endIndex, arr);
        let endTime1 = new Date().getTime();
        let r2 = regionTree.getRegionValue(startIndex, endIndex);
        let endTime2 = new Date().getTime();
        console.log("startIndex:" + startIndex);
        console.log("endIndex:" + endIndex);
        console.log("for求和为:" + r1 + "执行时间：" + (endTime1 - startTime1));
        console.log("线段树求和为:" + r2 + "执行时间：" + (endTime2 - endTime1));
        if (r1 !== r2) {
            alert("计算结果不正确");
        } else {
            console.log("计算正确");
        }
    }, 1000);
}

window.getRegionFor = function (startIndex, endIndex, current) {
    let value = null;
    for (let i = startIndex; i <= endIndex; i++) {
        value = regionTree.calcMethodContext.calc(current[i],value);
    }
    return value;
}

window.updateRegionFor = function (startIndex, endIndex, current, updateValue) {
    for (let i = startIndex; i <= endIndex; i++) {
        current[i] = regionTree.calcMethodContext.calc(current[i], updateValue);
    }
}

window.getRegionValue = function () {
    if (!isInt(startIndexEle.value)) {
        alert("区间开始值必须是整数！");
        return;
    }
    if (!isInt(endIndexEle.value)) {
        alert("区间结束值必须是整数！");
        return;
    }
    let startIndex = parseInt(startIndexEle.value);
    let endIndex = parseInt(endIndexEle.value);

    try {
        let result = regionTree.getRegionValue(startIndex, endIndex);
        let result2 = getRegionFor(startIndex, endIndex, current);
        result2 = "for循环统计结果为：" + result2;
        result = "线段树统计结果为：" + result;
        result = result + "," + result2;
        regionValueEle.innerText = result;
        regionTree.drawTreeOptimize(ctx, startX, startY, splitY);
    } catch (e) {
        alert(e);
    }
}

let preArr = null;
let current = [];
window.updateRegionValue = function () {
    if (!isInt(uStartIndexEle.value)) {
        alert("区间开始值必须是整数！");
        return;
    }
    if (!isInt(uEndIndexEle.value)) {
        alert("区间结束值必须是整数！");
        return;
    }
    if (!isInt(uValueEle.value)) {
        alert("更新的值必须是整数！");
        return;
    }
    let startIndex = parseInt(uStartIndexEle.value);
    let endIndex = parseInt(uEndIndexEle.value);
    let uValue = parseInt(uValueEle.value);
    try {
        if (!preArr) {
            preArr = JSON.parse(numsArrEle.value);
        }
        regionTree.update(startIndex, endIndex, uValue);
        regionTree.drawTreeOptimize(ctx, startX, startY, splitY);
        current = [];
        for (let i = 0; i < preArr.length; i++) {
            if (i >= startIndex && i <= endIndex) {
                current.push(regionTree.calcMethodContext.calc(preArr[i], uValue));
            } else {
                current.push(preArr[i]);
            }
        }
        preArr = current;
        let node = document.createElement("div");
        let eleStr = '再次更新后的数组为：' + JSON.stringify(current);
        node.textContent = eleStr;
        regionsEle.appendChild(node);
    } catch (e) {
        alert(e);
    }
}
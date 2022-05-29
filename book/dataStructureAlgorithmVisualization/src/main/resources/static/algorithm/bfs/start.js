import '../../common/css/header.css';
import ArrayExtend from "../../common/js/extends/ArrayExtend";
import NumberExtend from "../../common/js/extends/NumberExtend";
import ObjectExtend from "../../common/js/extends/ObjectExtend";
import BFS from "./BFS";

ArrayExtend.exec();
NumberExtend.exec();
ObjectExtend.exec();

let dfs = new BFS();
dfs.init();

let addRowEle = document.getElementById("addRow");
let addColEle = document.getElementById("addCol");
let setPersonRowEle = document.getElementById("setPersonRow");
let setPersonColEle = document.getElementById("setPersonCol");
let setDistRowEle = document.getElementById("setDistRow");
let setDistColEle = document.getElementById("setDistCol");
window.isInt = function (n) {
    return /^\d+$/.test(n);
}

window.onload = function () {
    setPersonRowEle.value = dfs.person.row;
    setPersonColEle.value = dfs.person.col;
    setDistRowEle.value = dfs.dist.row;
    setDistColEle.value = dfs.dist.col;
}

window.addV = function () {
    let addRowValue = addRowEle.value;
    let addColValue = addColEle.value;
    if (!isInt(addRowValue) || !isInt(addColValue)) {
        alert("row或col格式不正确！");
        return;
    }
    dfs.addBarrier(parseInt(addRowValue), parseInt(addColValue));
}

window.setPersonPosition = function () {
    let setPersonRowEleValue = setPersonRowEle.value;
    let setPersonColEleValue = setPersonColEle.value;
    if (!isInt(setPersonRowEleValue) || !isInt(setPersonColEleValue)) {
        alert("row或col格式不正确！");
        return;
    }
    let rv = parseInt(setPersonRowEleValue);
    let cv = parseInt(setPersonColEleValue);
    if (dfs.isBarrier(rv, cv)) {
        alert("不能将人物设置在障碍物上面！");
        return;
    }
    let e = dfs.getLiEle(rv, cv);
    if (!e) {
        alert("设置失败！坐标不在范围内！");
        return;
    }
    dfs.person.row = rv;
    dfs.person.col = cv;
    dfs.setPersonPositionAndDraw(dfs.person.row, dfs.person.col);
    dfs.init();
}

window.setDistPosition = function () {
    let setDistRowEleValue = setDistRowEle.value;
    let setDistColEleValue = setDistColEle.value;
    if (!isInt(setDistRowEleValue) || !isInt(setDistColEleValue)) {
        alert("row或col格式不正确！");
        return;
    }
    let rv = parseInt(setDistRowEleValue);
    let cv = parseInt(setDistColEleValue);
    if (dfs.isBarrier(rv, cv)) {
        alert("不能将终点设置在障碍物上面！");
        return;
    }
    let e = dfs.getLiEle(rv, cv);
    if (!e) {
        alert("设置失败！坐标不在范围内！");
        return;
    }
    if (rv === dfs.person.row && cv === dfs.person.col) {
        alert("终点不能设置在人物上！");
        return;
    }
    let oldDistEle = dfs.getLiEle(dfs.dist.row, dfs.dist.col);
    oldDistEle.style.backgroundColor = "pink";
    dfs.dist.row = rv;
    dfs.dist.col = cv;
    dfs.init();
}

window.toggleBarrier = function (row, col) {
    dfs.toggleBarrier(row, col);
}

window.startDfs = function () {
    let buttons = document.getElementsByTagName("button");
    for (let i = 0; i < buttons.length; i++) {
        buttons[i].disabled = true;
    }
    dfs.finish = false;

    function finished() {
        dfs.finish = true;
        for (let i = 0; i < buttons.length; i++) {
            buttons[i].disabled = false;
        }
    }

    function f(result) {
        if (result) {
            dfs.play(finished);
        } else {
            finished();
            alert("无法到达目的地！");
        }
    }

    dfs.startGo(f);
}
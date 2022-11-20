import '../../common/css/header.css';
import ArrayExtend from "../../common/js/extends/ArrayExtend";
import NumberExtend from "../../common/js/extends/NumberExtend";
import ObjectExtend from "../../common/js/extends/ObjectExtend";
import Reg from "./Reg";

ArrayExtend.exec();
NumberExtend.exec();
ObjectExtend.exec();

let textEle = document.getElementById("text");
let patternEle = document.getElementById("pattern");
let textInputEle = document.getElementById("textInput");

window.isInt = function (n) {
    return /^\d+$/.test(n);
}

window.resetText = function(){
    let newText = textInputEle.value;
    textEle.innerHTML = newText;
}

window.onload = function () {

}
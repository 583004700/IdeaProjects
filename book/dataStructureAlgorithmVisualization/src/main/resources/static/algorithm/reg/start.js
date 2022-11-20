import '../../common/css/header.css';
import ArrayExtend from "../../common/js/extends/ArrayExtend";
import NumberExtend from "../../common/js/extends/NumberExtend";
import ObjectExtend from "../../common/js/extends/ObjectExtend";
import Reg from "./Reg";

ArrayExtend.exec();
NumberExtend.exec();
ObjectExtend.exec();

let patternEle = document.getElementById("pattern");
let textInputEle = document.getElementById("textInput");

window.isInt = function (n) {
    return /^\d+$/.test(n);
}

window.search = function(){
    let pattern = patternEle.value;
    let str = textInputEle.value;
    let reg = new Reg(pattern);
    let result = reg.test(str);
    alert(result);
}

window.onload = function () {

}
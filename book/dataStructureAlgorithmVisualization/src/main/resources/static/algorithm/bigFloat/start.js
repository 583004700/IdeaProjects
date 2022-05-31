import '../../common/css/header.css';
import ArrayExtend from "../../common/js/extends/ArrayExtend";
import NumberExtend from "../../common/js/extends/NumberExtend";
import ObjectExtend from "../../common/js/extends/ObjectExtend";

import BigFloatOperation from "./bigFloat";

ArrayExtend.exec();
NumberExtend.exec();
ObjectExtend.exec();

let float1Ele = document.getElementById("float1");
let float2Ele = document.getElementById("float2");
let optSelectEle = document.getElementById("optSelect");
let resultEle = document.getElementById("result");
let oldResultEle = document.getElementById("oldResult");

window.isFloat = function (n) {
    return /^[-]?\d+\.?\d*$/.test(n);
}

window.calc = function(){
    let float1Value = float1Ele.value;
    let float2Value = float2Ele.value;
    if(!isFloat(float1Value) || !isFloat(float2Value)){
        alert("数字格式不正确!");
        return;
    }
    let optValue = optSelectEle.value;
    let resultValue = "";
    let oldValue = "";
    switch(optValue){
        case "+":
            resultValue = BigFloatOperation.add(float1Value,float2Value);
            oldValue = parseFloat(float1Value)+parseFloat(float2Value);
            break;
        case "-":
            resultValue = BigFloatOperation.subtract(float1Value,float2Value);
            oldValue = parseFloat(float1Value)-parseFloat(float2Value);
            break;
        case "*":
            resultValue = BigFloatOperation.multiply(float1Value,float2Value);
            oldValue = parseFloat(float1Value)*parseFloat(float2Value);
            break;
    }
    resultEle.innerHTML = resultValue;
    oldResultEle.innerHTML = oldValue;
}



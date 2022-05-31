import '../../common/css/header.css';
import ArrayExtend from "../../common/js/extends/ArrayExtend";
import NumberExtend from "../../common/js/extends/NumberExtend";
import ObjectExtend from "../../common/js/extends/ObjectExtend";

import BigIntOperation from "./bigint";

ArrayExtend.exec();
NumberExtend.exec();
ObjectExtend.exec();

let int1Ele = document.getElementById("int1");
let int2Ele = document.getElementById("int2");
let optSelectEle = document.getElementById("optSelect");
let resultEle = document.getElementById("result");

window.isInt = function (n) {
    return /^[-]?\d+$/.test(n);
}

window.calc = function(){
    let int1Value = int1Ele.value;
    let int2Value = int2Ele.value;
    if(!isInt(int1Value) || !isInt(int2Value)){
        alert("数字格式不正确!");
        return;
    }
    let optValue = optSelectEle.value;
    let resultValue = "";
    switch(optValue){
        case "+":
            resultValue = BigIntOperation.add(int1Value,int2Value);
            break;
        case "-":
            resultValue = BigIntOperation.subtract(int1Value,int2Value);
            break;
        case "*":
            resultValue = BigIntOperation.multiply(int1Value,int2Value);
            break;
    }
    resultEle.innerHTML = resultValue;
}



import '../../common/css/header.css';
import Pack01 from "./pack01";

window.isInt = function (n) {
    return /^\d+$/.test(n);
}

window.calc = function(){
    let w = document.getElementById("w").value.split(",");
    let v = document.getElementById("v").value.split(",");
    if(w.length !== v.length){
        alert("物品重量和物品价值长度不一致！");
        return;
    }
    for (let i = 0; i < w.length; i++) {
        if(!isInt(w[i].trim()) || !isInt(v[i].trim())){
            alert("物品价值和物品重量必须是整数！");
            return;
        }
        w[i] = parseInt(w[i]);
        v[i] = parseInt(v[i]);
    }
    let capacity = document.getElementById("capacity").value;
    if(!isInt(capacity)){
        alert("背包容量必须是整数！");
        return;
    }
    let pack01 = new Pack01(w,v);
    capacity = parseInt(capacity);
    let result = pack01.f(w.length-1, capacity);
    let gj = pack01.gj;
    let indexs = [];
    let values = [];
    for (let i = 0; i < gj[capacity].length; i++) {
        indexs.push(gj[capacity][i]);
        values.push(v[gj[capacity][i]]);
    }
    alert("所选物品下标分别为："+indexs+"\n所选物品价值分别为："+values+"\n最大值为："+result);
}

package com.demo.mydemo.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 购物车
 */
@Setter
@Getter
public class AppCart {

    int itemid;
    int action; // 1 添加产品进购物车 2 调整购物车数量
    int changeNum; // 数量变化
    int beforeNum;// 变化前数量
    int afterNum; //变化后数量
    Double price; //加入购物车时的单价
}

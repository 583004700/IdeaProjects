package com.atguigu.adapter.classadapter;

public class Phone {
    public void charging(IVoltage5V iVoltage5V){
        if(iVoltage5V.output5V() == 5){
            System.out.println("电压合适，可以充电");
        }
    }
}

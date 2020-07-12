package com.atguigu.builder;

public class Client {
    public static void main(String[] args) {
        AbstractHouse abstractHouse = new CommonHouse();
        abstractHouse.build();
    }
}

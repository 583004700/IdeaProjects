package com.atguigu.singleton.type1;

public class SingletonTest01 {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        Singleton singleton1 = Singleton.getInstance();
        System.out.println(singleton == singleton1);
    }
}

class Singleton {
    private Singleton(){

    }

    private final static Singleton singleton = new Singleton();

    public static Singleton getInstance(){
        return singleton;
    }
}
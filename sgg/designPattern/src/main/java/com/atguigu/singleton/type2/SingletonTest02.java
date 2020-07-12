package com.atguigu.singleton.type2;

public class SingletonTest02 {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        Singleton singleton1 = Singleton.getInstance();
        System.out.println(singleton == singleton1);
    }
}

class Singleton {
    private Singleton(){

    }

    private final static Singleton singleton;

    static {
        singleton = new Singleton();
    }

    public static Singleton getInstance(){
        return singleton;
    }
}
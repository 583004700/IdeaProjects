package com.atguigu.singleton.type8;

public class SingletonTest08 {
    public static void main(String[] args) {
        Singleton singleton = Singleton.INSTANCE;
        Singleton singleton1 = Singleton.INSTANCE;
        System.out.println(singleton == singleton1);
        singleton.sayOk();
    }
}

enum Singleton{
    INSTANCE;

    public void sayOk(){
        System.out.println("ok");
    }
}

package com.atguigu.singleton.type7;

public class SingletonTest07 {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        Singleton singleton1 = Singleton.getInstance();
        System.out.println(singleton == singleton1);
    }
}

class Singleton{
    private Singleton(){

    }

    public static class SingletonInstance{
        private static Singleton singleton = new Singleton();
    }

    public static Singleton getInstance(){
        return SingletonInstance.singleton;
    }
}

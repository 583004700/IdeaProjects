package com.atguigu.singleton.type4;

public class SingletonTest04 {
    public static void main(String[] args) throws Exception{
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    Singleton singleton1 = Singleton.getInstance();
                    System.out.println(singleton1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        Singleton singleton = Singleton.getInstance();
        System.out.println(singleton);
    }
}

//懒汉式线程安全的单例
class Singleton{
    private Singleton(){

    }

    private static Singleton singleton;

    public static Singleton getInstance() throws Exception{
        synchronized (Singleton.class) {
            if (singleton == null) {
                Thread.sleep(1000);
                singleton = new Singleton();
            }
        }
        return singleton;
    }
}

package com.atguigu.prototype.improve;

public class Client {
    public static void main(String[] args) throws Exception{
        Sheep friend = new Sheep("jack",2,"黑色",null);

        Sheep sheep = new Sheep("tom", 1, "白色",friend);

        Sheep sheep1 = (Sheep) sheep.clone();
        Sheep sheep2 = (Sheep) sheep.clone();

        System.out.println(sheep);
        System.out.println(sheep1);
        System.out.println(sheep2);
        //引用的是同一个对象
        System.out.println(sheep.getFriend() == sheep2.getFriend());
    }
}

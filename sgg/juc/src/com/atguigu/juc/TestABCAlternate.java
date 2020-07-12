package com.atguigu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestABCAlternate {
    static AlternateDemo alternateDemo = new AlternateDemo();

    public static void main(String[] args) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=10;i++) {
                    alternateDemo.loopA(i);
                }
            }
        },"A").start();

        new Thread( new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=10;i++) {
                    alternateDemo.loopB(i);
                }
            }
        },"B").start();

        new Thread( new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=10;i++) {
                    alternateDemo.loopC(i);
                }
            }
        },"C").start();
    }
}

class AlternateDemo{

    private int number = 1;

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void loopA(int total){
        lock.lock();
        try {
            if(number != 1){
                condition1.await();
            }
            for(int i=1;i<=5;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+total);
            }
            number = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void loopB(int total){
        lock.lock();
        try {
            if(number != 2){
                condition2.await();
            }
            for(int i=1;i<=15;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+total);
            }
            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void loopC(int total){
        lock.lock();
        try {
            if(number != 3){
                condition3.await();
            }
            for(int i=1;i<=20;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+total);
            }
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}

package com.atguigu.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 写写/读写 互斥
 * 读读不互斥
 */
public class TestReadWriteLock {
    public static void main(String[] args) {
        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();

        for(int i=0;i<100;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    readWriteLockDemo.get();
                }
            }, String.valueOf(i)).start();
        }

        for(int i=0;i<100;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    readWriteLockDemo.set(1);
                }
            }, String.valueOf(i)).start();
        }
    }
}

class ReadWriteLockDemo{
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    private int number = 0;

    public void get(){
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"begin");
            System.out.println(Thread.currentThread().getName()+":"+number);
        } finally {
            System.out.println(Thread.currentThread().getName()+"end");
            lock.readLock().unlock();
        }

    }

    public void set(int number){
        lock.writeLock().lock();
        try {
            System.out.println("set"+Thread.currentThread().getName()+"begin");
            this.number = number;
        } finally {
            System.out.println("set"+Thread.currentThread().getName()+"end");
            lock.writeLock().unlock();
        }
    }
}



package com.atguigu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestProductorAndConsumerForLock {

    public static void main(String[] args) {
        ClerkForLock clerk = new ClerkForLock();

        ProductorForLock pro = new ProductorForLock(clerk);
        ConsumerForLock cus = new ConsumerForLock(clerk);

        new Thread(pro,"生产者A").start();
        new Thread(cus,"消费者B").start();

        new Thread(pro,"生产者C").start();
        new Thread(cus,"消费者D").start();
    }
}

//店员
class ClerkForLock {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private int product = 0;

    public void get(){
        lock.lock();
        try {
        /*if*/
            while(product >= 1){
                System.out.println("产品已满");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }//else{
            System.out.println(Thread.currentThread().getName()+":"+ ++product);

            condition.signalAll();
            //}
        } finally {
            lock.unlock();
        }
    }

    public void sale(){
        lock.lock();
        try {
        /*if*/
            while(product <= 0){
                System.out.println(Thread.currentThread().getName()+"缺货！");

                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }//else{
            try {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(20);
            }catch (Exception e){

            }
            System.out.println(Thread.currentThread().getName()+":"+ --product);
            condition.signalAll();
            //}
        } finally {
            lock.unlock();
        }
    }
}

//生产者
class ProductorForLock implements Runnable{
    private ClerkForLock clerk;
    
    public ProductorForLock(ClerkForLock clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.get();
        }
    }
}

class ConsumerForLock implements Runnable{
    private ClerkForLock clerk;

    public ConsumerForLock(ClerkForLock clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}
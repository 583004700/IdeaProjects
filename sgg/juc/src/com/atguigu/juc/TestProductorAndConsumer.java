package com.atguigu.juc;

public class TestProductorAndConsumer {

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
class Clerk{
    private int product = 0;

    public synchronized void get(){
        /*if*/while(product >= 1){
            System.out.println("产品已满");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }//else{
            System.out.println(Thread.currentThread().getName()+":"+ ++product);

            this.notifyAll();
        //}
    }

    public synchronized void sale(){
        /*if*/while(product <= 0){
            System.out.println(Thread.currentThread().getName()+"缺货！");

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }//else{
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(2000);
        }catch (Exception e){

        }
            System.out.println(Thread.currentThread().getName()+":"+ --product);
            this.notifyAll();
        //}
    }
}

//生产者
class Productor implements Runnable{
    private ClerkForLock clerk;
    
    public Productor(ClerkForLock clerk){
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

class Consumer implements Runnable{
    private ClerkForLock clerk;

    public Consumer(ClerkForLock clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}
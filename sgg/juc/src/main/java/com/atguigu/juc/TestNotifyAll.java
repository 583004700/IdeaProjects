package com.atguigu.juc;

public class TestNotifyAll {
    public static void main(String[] args) throws Exception{
        C c = new C();

        NotifyAllDemo notifyAllDemo = new NotifyAllDemo(c);

        Thread t1 = new Thread(notifyAllDemo,"线程1");
        Thread t2 = new Thread(notifyAllDemo,"线程2");
        Thread t3 = new Thread(notifyAllDemo,"线程3");
        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(2000);
        C c1 = new C();
        NotifyAllDemo1 notifyAllDemo1 = new NotifyAllDemo1(c1);
        Thread t4 = new Thread(notifyAllDemo1,"线程4");
        t4.start();
    }
}

class C{
    public synchronized void w(){
        try {
            this.wait();
            System.out.println(Thread.currentThread().getName()+"等待");
        }catch (Exception e){

        }
    }

    public synchronized void n(){
        this.notifyAll();
    }
}

class NotifyAllDemo implements Runnable{

    private C c;

    public NotifyAllDemo(C c){
        this.c = c;
    }

    @Override
    public void run() {
        c.w();
    }
}

class NotifyAllDemo1 implements Runnable{
    private C c;

    public NotifyAllDemo1(C c){
        this.c = c;
    }

    @Override
    public void run() {
        c.n();
    }
}
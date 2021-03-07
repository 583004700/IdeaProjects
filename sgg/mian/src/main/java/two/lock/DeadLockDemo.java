package two.lock;

import java.util.concurrent.TimeUnit;

class DeadLock{
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void m1(){
        synchronized (lock1){
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m2();
        }
    }

    public void m2(){
        synchronized (lock2){
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m1();
        }
    }
}

public class DeadLockDemo {
    public static void main(String[] args) throws Exception{
        DeadLock deadLock = new DeadLock();
        Thread t1 = new Thread(()->{deadLock.m1();});
        Thread t2 = new Thread(()->{deadLock.m2();});
        t1.start();
        t2.start();
    }
}

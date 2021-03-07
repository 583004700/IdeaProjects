package two.lock;

import java.util.concurrent.atomic.AtomicReference;

public class MySpinLock {
    //原子引用线程
    private AtomicReference<Thread> atomicReference = new AtomicReference<Thread>();

    public void myLock(){
        Thread thread = Thread.currentThread();
        while(!atomicReference.compareAndSet(null,thread)){}
    }

    public void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
    }

    public static void main(String[] args) {
        SpinLockTest test = new SpinLockTest();

        new Thread(new Runnable() {
            @Override
            public void run() {
               test.test();
            }
        },"A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
               test.test();
            }
        },"B").start();
    }
}

class SpinLockTest{
    MySpinLock mySpinLock = new MySpinLock();
    public void test(){
        mySpinLock.myLock();
        System.out.println("线程"+Thread.currentThread()+"正在执行");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程"+Thread.currentThread()+"正在结束");
        mySpinLock.myUnlock();
    }
}

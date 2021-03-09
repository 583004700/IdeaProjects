package three.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LockSupportDemo {
    static Object objectLock = new Object();
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        //synchronizedWaitNotify();
        //lockAwaitSignal();

        /*
            通知可以在阻塞之前执行
         */
        Thread a = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t----come in");
            // 消耗一个凭证，如果没有了凭证，则阻塞
            LockSupport.park();     //被阻塞...等待通知放行
            System.out.println(Thread.currentThread().getName() + "\t----被唤醒");
        }, "a");
        a.start();

        Thread b = new Thread(() -> {
            // 增加凭证，但凭证最多只有一个
            LockSupport.unpark(a);
            System.out.println(Thread.currentThread().getName() + "\t----通知");
        },"b");
        b.start();
    }

    private static void lockAwaitSignal() {
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t-----come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "\t-----被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "A").start();

        new Thread(() -> {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "\t-----通知");
            condition.signal();
            lock.unlock();
        }, "B").start();
    }

    /*
        1、wait和notify需要是同一个对象在synchronized代码块中成对出现
        2、Object需要先wait，再notify
    */
    private static void synchronizedWaitNotify() {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (objectLock) {
                System.out.println(Thread.currentThread().getName() + "\t-----come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t-----被唤醒");
            }
        }, "A").start();


        new Thread(() -> {
            synchronized (objectLock) {
                System.out.println(Thread.currentThread().getName() + "\t-----通知");
                objectLock.notify();
            }
        }, "B").start();
    }
}

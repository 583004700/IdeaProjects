package three.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
    可重入锁：可重复可递归调用的锁，在外层使用锁之后，在内层仍然可以使用，并且不发生死锁，这样的锁就叫做重入锁。

 */
public class ReEnterLockDemo {
    static Lock lock = new ReentrantLock();

    public void m1(){
        new Thread(() -> {
            System.out.println("m1 this:"+this.getClass().getName());
            lock.lock();
            try {
                System.out.println("-----外层");
                lock.lock();
                try {
                    System.out.println("-----内层");
                } finally {
                    lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        }, "t1").start();
    }

    public void m2(){
        new Thread(new Runnable() {
            private int a = 10;
            @Override
            public void run() {
                System.out.println("m2 this:"+this.getClass().getName());
                lock.lock();
                try {
                    System.out.println("-----外层");
                    lock.lock();
                    try {
                        System.out.println("-----内层");
                    } finally {
                        lock.unlock();
                    }
                } finally {
                    lock.unlock();
                }
            }
        }, "t2").start();
    }

    public static void main(String[] args) {
        ReEnterLockDemo r1 = new ReEnterLockDemo();
        r1.m1();
        r1.m2();
    }
}

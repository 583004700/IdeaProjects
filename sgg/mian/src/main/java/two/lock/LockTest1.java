package two.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest1 {
    public static void sleep(TimeUnit timeUnit,int timeout){
        try {
            timeUnit.sleep(timeout);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //公平锁会按顺序等待，非公平锁会尝试获取锁，没获取到则等待
        ReentrantLock reentrantLock = new ReentrantLock(true);

        Thread t1 = new Thread(()->{
            reentrantLock.lock();
            System.out.println(Thread.currentThread().getName());
            sleep(TimeUnit.SECONDS,11);
            reentrantLock.unlock();
        },"t");
        t1.start();

        for(int i=0;i<10;i++){
            Thread t = new Thread(()->{
                reentrantLock.lock();
                System.out.println(Thread.currentThread().getName());
                reentrantLock.unlock();
            },"t"+i);
            t.start();
            sleep(TimeUnit.SECONDS,1);
        }
    }
}

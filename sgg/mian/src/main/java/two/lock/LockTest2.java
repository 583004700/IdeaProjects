package two.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁
 */
class Phone implements Runnable{
    ReentrantLock reentrantLock = new ReentrantLock();

    public synchronized void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getName()+"\t invoked sendSMS");

        sendEmail();
    }
    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getName()+"\t invoked sendEmail");
    }

    @Override
    public void run() {
        try {
            sendSMS2();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendSMS2() throws Exception{
        reentrantLock.lock();
        System.out.println(Thread.currentThread().getName()+"\t invoked sendSMS2");
        sendEmail2();
        reentrantLock.unlock();
    }
    public void sendEmail2() throws Exception{
        reentrantLock.lock();
        System.out.println(Thread.currentThread().getName()+"\t invoked sendEmail2");
        reentrantLock.unlock();
    }
}

public class LockTest2 {
    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(()->{
            try {
                phone.sendSMS();
            }catch (Exception e){
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(phone,"t2").start();
    }
}

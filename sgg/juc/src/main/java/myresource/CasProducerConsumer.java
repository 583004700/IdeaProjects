package myresource;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 在允许不保证每个线程都能成功完成某个操作时，可以使用cas
 */
class Contain{
    private static int count;
    private static int[] datas = new int[5];
    private static ThreadLocal<Integer> sizeThreadL = new ThreadLocal<Integer>();
    private static AtomicReference<Integer> sizeReference = new AtomicReference<Integer>(0);
    public void incre() throws InterruptedException {
        if(sizeReference.get() < datas.length) {
            //因为局部变量也是线程私有的，所以可以不用sizeThreadL，可以直接用 int threadSize = sizeReference.get()
            sizeThreadL.set(sizeReference.get());
            if (sizeReference.compareAndSet(sizeThreadL.get(), sizeReference.get() + 1)) {
                datas[sizeThreadL.get()] = count;
                System.out.println("生产数据:" + count);
                //此处不保证生产的数据没有重复，如果没有重复数据，那消费时也一定不会消费到相同数据，逻辑无问题
                count++;
            }
        }
    }
    public void sub() throws InterruptedException {
        if(sizeReference.get() > 0) {
            //因为局部变量也是线程私有的，所以可以不用sizeThreadL，可以直接用 int threadSize = sizeReference.get()
            sizeThreadL.set(sizeReference.get());
            if (sizeReference.compareAndSet(sizeThreadL.get(), sizeReference.get() - 1)) {
                int data = datas[sizeThreadL.get()-1];
                System.out.println("线程：" + Thread.currentThread() + new Date() + "消费数据:" + data);
            }
        }
    }
}

class Producer extends Contain implements Runnable {
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                incre();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Contain implements Runnable {
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(100);
                sub();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class CasProducerConsumer {
    public static void main(String[] args) {
        Producer pr = new Producer();
        Thread p = new Thread(pr);
        p.start();
        Consumer cust = new Consumer();
        Thread t = new Thread(cust);
        Thread t2 = new Thread(cust);
        Thread t3 = new Thread(cust);
        Thread t4 = new Thread(cust);
        t.start();
        t2.start();
        t3.start();
        t4.start();

    }
}

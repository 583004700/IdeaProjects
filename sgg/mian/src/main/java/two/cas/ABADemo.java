package two.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {
    static AtomicReference<Integer> atomicReference = new AtomicReference<Integer>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<Integer>(100,1);

    public static void main(String[] args) {


        new Thread(()->{
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
        },"t1").start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100,2019)+"\t"+atomicReference.get());
        },"t2").start();

        System.out.println("=============以下是ABA问题的解决============");

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第一次版本号："+stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
                atomicStampedReference.compareAndSet(100,101,stamp,stamp+1);
                System.out.println(Thread.currentThread().getName()+"\t第二次版本号："+atomicStampedReference.getStamp());
                stamp = atomicStampedReference.getStamp();
                atomicStampedReference.compareAndSet(101,100,stamp,stamp+1);
                System.out.println(Thread.currentThread().getName()+"\t第三次版本号："+atomicStampedReference.getStamp());
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        },"t3").start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第一次版本号："+stamp);
            try {
                TimeUnit.SECONDS.sleep(3);
                boolean result = atomicStampedReference.compareAndSet(100,2019,stamp,stamp+1);
                System.out.println("是否修改成功："+result);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        },"t4").start();
    }
}

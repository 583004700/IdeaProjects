package two.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newScheduledThreadPool(0);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    System.out.println(Thread.currentThread().getName()+"\t"+i);
                }
            }
        };

        for(int i=0;i<10;i++){
            threadPool.submit(runnable);
        }
    }
}

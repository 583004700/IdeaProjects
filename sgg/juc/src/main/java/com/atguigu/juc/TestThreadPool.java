package com.atguigu.juc;

import java.util.concurrent.*;

/**
 * 工具类：Executors
 * ExecutorService newFixedThreadPool()
 * ExecutorService newCachedThreadPool()
 * ExecutorService newSingleThreadExecutor()
 *
 * ScheduledExecutorService newScheduledThreadPool() 创建固定大小的线程池，可以延迟或定时执行任务。
 */
public class TestThreadPool {
    public static void main(String[] args) throws Exception{
        ThreadPoolDemo threadDemo = new ThreadPoolDemo();

        ExecutorService pool = Executors.newFixedThreadPool(5);


        ScheduledExecutorService pool1 = Executors.newScheduledThreadPool(5);

        for(int i=0;i<5;i++) {
            Future future1 = pool1.schedule(new Callable<Integer>() {
                int i = 0;

                @Override
                public Integer call() throws Exception {
                    for (int k = 0; k < 100; k++) {
                        i += k;
                    }
                    return i;
                }
            }, 3, TimeUnit.SECONDS);
            System.out.println(future1.get());
        }
        pool1.shutdown();



        /*Future future1 = pool.submit(new Callable<Integer>() {
            int i = 0;
            @Override
            public Integer call() throws Exception {
                for(int k=0;k<100;k++){
                    i+=k;
                }
                return i;
            }
        });

        System.out.println(future1.get());

        pool.shutdown();*/



        /*pool.submit(threadDemo);
        pool.submit(threadDemo);
        pool.shutdown();*/


    }
}

class ThreadPoolDemo implements Runnable{
    private int i = 0;

    @Override
    public void run() {
        while(i<=100){
            System.out.println(Thread.currentThread().getName()+":"+i++);
        }
    }
}

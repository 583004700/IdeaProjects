package com.atguigu.juc;

import java.util.concurrent.*;

/**
 * 一、创建执行线程的方式三：实现Callable接口
 * 执行Callable方式，需要FutureTask实现类的支持，用于接收运算结果
 */
public class TestCallable {
    public static void main(String[] args) {
        CallableDemo cd = new CallableDemo(1,100);

        //FutureTask<Integer> result = new FutureTask<Integer>(cd);

        MyFutureTask<Integer> result = new MyFutureTask<>(cd);

        new Thread(result).start();

        //接收线程运算后的结果
        try {
            System.out.println("before");
            Integer sum = result.get();
            System.out.println("after");
            System.out.println(sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class MyFutureTask<T> implements RunnableFuture<T> {
    private volatile T result;

    Callable<T> callable;
    public MyFutureTask(Callable<T> callable){
        this.callable = callable;
    }

    @Override
    public void run() {
        try {
            result = callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        while(result == null){

        }
        return result;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }
}

class CallableDemo implements Callable<Integer>{
    private int start;
    private int end;

    public CallableDemo(int start,int end){
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;

        for (int i = start; i <= end; i++) {
            Thread.sleep(1);
            sum += i;
        }

        return sum;
    }
}

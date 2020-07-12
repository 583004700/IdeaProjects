package com.atguigu.juc;

import java.util.concurrent.TimeUnit;

public class TestCompareAndSwap {

    public static void main(String[] args) {
        final CompareAndSwap cas = new CompareAndSwap();
        for (int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(cas.getAndIncrement());
                }
            }).start();
        }
    }
}

class CompareAndSwap{
    private int value;

    public int getValue(){
        return value;
    }

    public synchronized int compareAndSwap(int expectedValue, int newValue){
        int old = value;
        System.out.println("------------");
        if(expectedValue == old){
            value = newValue;
        }
        return old;
    }

    public int getAndIncrement(){
        int old;
        int g;
        do {
            old = this.getValue();
            g = this.compareAndSwap(this.getValue(), this.getValue() + 1);
        }while(old != g);
        return old;
    }

}

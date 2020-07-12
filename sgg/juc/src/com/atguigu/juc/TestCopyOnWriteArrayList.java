package com.atguigu.juc;


import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestCopyOnWriteArrayList {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            HelloThread ht = new HelloThread();
            new Thread(ht).start();
        }
    }
}

class HelloThread implements Runnable{

    //private static List<String> list = Collections.synchronizedList(new ArrayList<>());

    private static CopyOnWriteArrayList list = new CopyOnWriteArrayList();

    static{
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {
        Iterator<String> it = list.iterator();

        while(it.hasNext()){
            System.out.println(it.next());
            list.add("AA");
        }
    }
}
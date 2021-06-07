package com.atguigu.juc;

public class TestVolatile {

    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();

        new Thread(td).start();

        while(true){
            if(td.isFlag()){
                System.out.println("------------");
                break;
            }
        }
    }
}

class ThreadDemo implements Runnable{

    private volatile boolean flag = false;

    @Override
    public void run() {
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){

        }
        flag = true;
        System.out.println("flag=" + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}

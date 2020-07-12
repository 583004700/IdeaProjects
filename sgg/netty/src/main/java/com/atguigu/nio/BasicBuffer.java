package com.atguigu.nio;

import java.nio.IntBuffer;

public class BasicBuffer {
    public static void main(String[] args) {
        //创建一个Buffer,大小为5，可以存放5个int


        //capacity  容量
        //limit 读写操作的终点
        //position 下一次读写缓冲区的位置
        //mark 用来保存position的位置，调用reset方法，position位置 = mark
        IntBuffer intBuffer = IntBuffer.allocate(5);
        //向buffer   存放数据
//        intBuffer.put(10);
//        intBuffer.put(11);
//        intBuffer.put(12);
//        intBuffer.put(13);
//        intBuffer.put(14);

        for(int i=0;i<intBuffer.capacity();i++){
            intBuffer.put(i * 2);
        }
        //读写切换
        intBuffer.flip();
        intBuffer.position(1);
        while(intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
        intBuffer.position(3);
        intBuffer.put(5);
    }
}

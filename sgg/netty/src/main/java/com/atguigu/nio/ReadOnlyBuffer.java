package com.atguigu.nio;

import java.nio.ByteBuffer;

public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for(int i=0;i<64;i++){
            buffer.put((byte)i);
        }
        buffer.flip();
        ByteBuffer byteBuffer = buffer.asReadOnlyBuffer();
        System.out.println(byteBuffer.getClass());

        while(byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }
    }
}

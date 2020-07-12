package com.atguigu.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel03 {
    public static void main(String[] args) throws IOException {
        File file = new File("d:/file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("d:/file02.txt");
        FileChannel channel1 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        while(true){
            byteBuffer.clear();

            int count = channel.read(byteBuffer);
            if(count != -1){
                byteBuffer.flip();
                channel1.write(byteBuffer);
            }else{
                break;
            }
        }

        fileInputStream.close();
        fileOutputStream.close();
    }
}

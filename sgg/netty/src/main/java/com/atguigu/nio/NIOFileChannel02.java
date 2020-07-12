package com.atguigu.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel02 {
    public static void main(String[] args) throws IOException {
        File file = new File("d:/file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());

        channel.read(byteBuffer);
        fileInputStream.close();
        System.out.println(new String(byteBuffer.array()));
        
        System.out.println(channel.isOpen());
    }
}

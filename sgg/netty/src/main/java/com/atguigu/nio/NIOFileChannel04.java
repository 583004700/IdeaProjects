package com.atguigu.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class NIOFileChannel04 {
    public static void main(String[] args) throws IOException {
        File file = new File("d:/file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("d:/file02.txt");
        FileChannel channel1 = fileOutputStream.getChannel();

        channel1.transferFrom(channel,0,channel.size());

        fileInputStream.close();
        fileOutputStream.close();
    }
}

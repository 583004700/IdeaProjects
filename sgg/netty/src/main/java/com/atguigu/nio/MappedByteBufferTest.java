package com.atguigu.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile rw = new RandomAccessFile("d:/file01.txt", "rw");
        FileChannel channel = rw.getChannel();
        /**
         * 0代表起始位置
         * 5表示映射到内存的大小
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0,(byte)'H');
        map.put(3,(byte) '9');
        rw.close();

    }
}

package com.atguigu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering：将数据写入到buffer时，可以采用buffer数组，依次写入 【分散】
 * Gathering：从buffer读取数据时，可以采用buffer数组，依次读
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {
        //使用ServerSocketChannel 和 SocketChannel 网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8;
        while(true){
            int byteRead = 0;
            while(byteRead < messageLength){
                long l = socketChannel.read(byteBuffers);
                byteRead += l;
                System.out.println("byteRead:"+byteRead);

                Arrays.asList(byteBuffers).stream().map(b->"position:" + b.position()+"limit:"+b.limit()).forEach(System.out::println);
            }

            //将所有buffer进行flip
            Arrays.asList(byteBuffers).forEach(b->b.flip() );
            //将数据读出显示到客户端
            long byteWrite = 0;
            while(byteWrite < messageLength){
                long l = socketChannel.write(byteBuffers);
                byteWrite += l;
            }

            //将所有的buffer 进行clear
            Arrays.asList(byteBuffers).forEach(b->{
                b.clear();
            });
            System.out.println("byteRead:="+byteRead+"byteWrite:"+byteWrite+"messageLength:"+messageLength);
        }

    }
}

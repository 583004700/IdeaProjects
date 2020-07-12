package com.atguigu.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        serverSocketChannel.bind(inetSocketAddress);
        Selector selector = Selector.open();
        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //keys是所有注册的selectionKey
        System.out.println("keys:"+selector.keys());

        while(true){
            if(selector.select(1000) == 0){
                System.out.println("服务器等待1s");
                continue;
            }
            //是监听到的selectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                System.out.println("selectionKey:"+selectionKey);
                if(selectionKey.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }else if(selectionKey.isReadable()){
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    ByteBuffer buffer = (ByteBuffer)selectionKey.attachment();
                    long l = channel.read(buffer);
                    //Thread.sleep(20000);
                    System.out.println("form 客户端:"+new String(buffer.array(),0,(int)l));
                }
                iterator.remove();
            }
        }
    }
}

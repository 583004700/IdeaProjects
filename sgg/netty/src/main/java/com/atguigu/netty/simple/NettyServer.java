package com.atguigu.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = null;
        EventLoopGroup workerGroup = null;
        try {
            //创建BossGroup 和 WorkerGroup
            //说明
            //1.创建两个线程组 bossGroup 和 workerGroup
            //2. bossGroup 只是处理连接请求，真正的业务处理交给 workerGroup
            //3.两个都是无限循环
            //4.bossGroup 和 workerGroup 含有的子线程(NIOEventLoop)的个数默认
            //默认实际cpu核数 * 2
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();

            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)  //设置两个线程组
                    .channel(NioServerSocketChannel.class)    //使用NioSocketChannel作为服务器通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)  //设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)    //设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { //创建一个通道初始对象
                        //给pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //可以使用一个集合管理所有的SocketChannel,然后就可以发送消息给指定的客户端
                            System.out.println("客户端socketChannel:"+ch.hashCode());
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });     //给workerGroup 的EventLoop 对应的管道设置处理器

            System.out.println("......服务器 is ready...");
            //绑定一个端口并且同步，生成了一个ChannelFuture对象
            ChannelFuture cf = bootstrap.bind(6668).sync();

            //给cf 注册监听器，监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(cf.isSuccess()){
                        System.out.println("监听端口 6668 成功");
                    }else{
                        System.out.println("监听端口 6668 失败");
                    }
                }
            });

            System.out.println("1");
            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
            System.out.println("2");
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.out.println("3");
        }
    }
}

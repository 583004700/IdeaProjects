package com.atguigu.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取数据
     *  1.ChannelHandlerContext ctx:上下文对象，含有 管道pipeline,通道，地址等
     *  2.Object msg :就是客户端发送的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //比如这里有一个非常耗时的业务-> 异步执行->提交该channel对应的nioeventloop的taskQueue中

//        Thread.sleep(10000);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端1",CharsetUtil.UTF_8));

        //解决方法1 用户程序自定义的普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(10000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端1",CharsetUtil.UTF_8));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        //解决方法2 定时任务
        //ctx.channel().eventLoop().schedule()

        System.out.println("go on ...");

        /*System.out.println("服务器读取...线程："+Thread.currentThread().getName());
        System.out.println("server ctx = "+ctx);
        System.out.println("看看channel 和 pipeline的关系");
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline(); //本质是一个双向链表

        //这个ByteBuf是netty的，不是jdk的
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是："+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址："+ctx.channel().remoteAddress());*/
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入缓存并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端2",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

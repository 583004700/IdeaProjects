package com.atguigu.mq;

import com.atguigu.mq.bean.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot02AmqpApplicationTests {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void createExchange() {
        //创建交换器
//        amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
//        System.out.println("创建完成");

        //创建队列
//        amqpAdmin.declareQueue(new Queue("amqpadmin.queue",true));

        //创建绑定规则
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue",
                Binding.DestinationType.QUEUE,
                "amqpadmin.exchange","amqp.haha",null));
    }

    /**
     * 1、点对点
     */
    @Test
    public void contextLoads() {
//        Message需要自己构造一个;定义消息体内容和消息头
//        rabbitTemplate.send(exchange,routeKey,message);

        //object默认当成消息体，只需要传入要发送的对象，自动序列化发送给mq

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msg", "这是第一个消息");
        map.put("data", Arrays.asList("hello", 123, true));
        //对象被默认序列化以后发送出去
        rabbitTemplate.convertAndSend("exchange.direct", "atguigu.news", new Book().setBookName("西游记").setAuthor("吴"));
    }

    @Test
    public void receive() {
        Object o = rabbitTemplate.receiveAndConvert("atguigu.news");
        System.out.println("o.class:" + o.getClass());
        System.out.println(o);
    }

    /**
     * 广播
     */
    @Test
    public void sendMsg() {
        rabbitTemplate.convertAndSend("exchange.fanout", "", new Book().setAuthor("吴").setBookName("西游记"));
    }
}

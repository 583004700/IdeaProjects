package com.atguigu.mq.service;

import com.atguigu.mq.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    //要生效，要开启 @EnableRabbit
    @RabbitListener(queues = "atguigu.news")
    public void receive(Book book) {
        System.out.println("收到消息："+book);
    }

    @RabbitListener(queues = "atguigu")
    public void receive02(Message message){
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties());
    }
}

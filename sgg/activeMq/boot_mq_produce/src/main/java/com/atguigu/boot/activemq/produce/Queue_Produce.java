package com.atguigu.boot.activemq.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.UUID;

@Component
public class Queue_Produce {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    public void produceMsg(){
        jmsMessagingTemplate.convertAndSend(queue, UUID.randomUUID().toString());
    }
    //间隔时间3秒钟定投
    @Scheduled(fixedDelay = 3000)
    public void produceMsgScheduled(){
        System.out.println("定时发送消息");
        produceMsg();
    }
}

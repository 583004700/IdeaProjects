package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JmsConsumer_TX {
    final static Logger LOG = LoggerFactory.getLogger(JmsConsumer_TX.class);

    public static final String ACTIVEMQ_URL = "tcp://192.168.33.13:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws Exception {
        //创建连接工厂，使用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        LOG.info("启动成功");
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(queue);


        TextMessage textMessage = (TextMessage)consumer.receive(1000L);
        while(textMessage != null){
            System.out.println(textMessage.getText());
            textMessage.acknowledge();
            textMessage = (TextMessage) consumer.receive(1000L);
        }

        //如果开启了事务，没有commit，会被多次消费
        //session.commit();

        consumer.close();
        connection.close();

    }
}

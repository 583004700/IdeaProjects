package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class JmsProduce_Topic {
    final static Logger LOG = LoggerFactory.getLogger(JmsProduce.class);

    public static final String ACTIVEMQ_URL = "tcp://192.168.33.13:61616";
    public static final String TOPIC_NAME = "topic-atguigu";

    public static void main(String[] args) throws Exception {
        //创建连接工厂，使用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        LOG.info("启动成功");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer producer = session.createProducer(topic);
        for (int i = 1; i <= 15; i++) {
            TextMessage textMessage = session.createTextMessage("topic" + i);
            producer.send(textMessage);
        }
        producer.close();
        connection.close();

        LOG.info("消息发布到MQ完成！");
    }
}

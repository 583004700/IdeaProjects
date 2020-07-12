package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class JmsConsumer_Topic {
    final static Logger LOG = LoggerFactory.getLogger(JmsConsumer_Topic.class);

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
        MessageConsumer consumer = session.createConsumer(topic);


//        TextMessage textMessage = (TextMessage)consumer.receive();
//        System.out.println(textMessage.getText());

        //消息监听方式消费
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage)message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

//        consumer.close();
//        connection.close();

    }
}

package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

public class JmsConsumer_Topic_Persist {
    final static Logger LOG = LoggerFactory.getLogger(JmsConsumer_Topic_Persist.class);

    public static final String ACTIVEMQ_URL = "tcp://192.168.33.13:61616";
    public static final String TOPIC_NAME = "topic-persist";

    public static void main(String[] args) throws Exception {
        //创建连接工厂，使用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("z3");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark...");
        connection.start();

        Message message = topicSubscriber.receive();

        while(message != null){
            TextMessage textMessage = (TextMessage)message;
            System.out.println(textMessage.getText());
            message = topicSubscriber.receive(2000L);
        }

//        consumer.close();
//        connection.close();

    }
}

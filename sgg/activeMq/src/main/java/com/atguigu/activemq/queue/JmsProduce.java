package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class JmsProduce {
    final static Logger LOG = LoggerFactory.getLogger(JmsProduce.class);

    //public static final String ACTIVEMQ_URL = "tcp://192.168.33.13:61616";
    public static final String ACTIVEMQ_URL = "nio://192.168.33.13:61618";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws Exception {
        //创建连接工厂，使用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        LOG.info("启动成功");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer producer = session.createProducer(queue);
        //设置消息持久化
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        for (int i = 1; i <= 15; i++) {
            TextMessage textMessage = session.createTextMessage("msg" + i);
            producer.send(textMessage);
            textMessage.setStringProperty("c01","vip");

            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("k1","v");
            producer.send(mapMessage);
        }
        producer.close();
        connection.close();

        LOG.info("消息发布到MQ完成！");
    }
}

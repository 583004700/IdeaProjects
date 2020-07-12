package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JmsProduce_TX {
    final static Logger LOG = LoggerFactory.getLogger(JmsProduce_TX.class);

    public static final String ACTIVEMQ_URL = "tcp://192.168.33.13:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws Exception {
        //创建连接工厂，使用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        LOG.info("启动成功");
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer producer = session.createProducer(queue);
        //设置消息持久化
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        for (int i = 1; i <= 15; i++) {
            TextMessage textMessage = session.createTextMessage("msg" + i);
            producer.send(textMessage);
            textMessage.setStringProperty("c01","vip");

        }
        //如果开启了事务，需要提交
        session.commit();
        //如果有异常，可以使用rollback回滚
        //session.rollback();
        producer.close();
        connection.close();

        LOG.info("消息发布到MQ完成！");
    }
}

package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class JmsConsumer {
    final static Logger LOG = LoggerFactory.getLogger(JmsConsumer.class);

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
        MessageConsumer consumer = session.createConsumer(queue);


//        TextMessage textMessage = (TextMessage)consumer.receive();
//        System.out.println(textMessage.getText());

        //消息监听方式消费
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if(message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }else if(message instanceof MapMessage){
                    MapMessage mapMessage = (MapMessage) message;
                    try {
                        System.out.println(mapMessage.getString("k1"));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

//        consumer.close();
//        connection.close();

    }
}

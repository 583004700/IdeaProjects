package com.atguigu.activemq.queue.embed;

import org.apache.activemq.broker.BrokerService;

//嵌入式的activemq
public class EmbedBroker {
    public static void main(String[] args) throws Exception{
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}

package com.demo.kafka.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class PartitionProducer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1.创建kafka生产者的配置信息
        Properties properties = new Properties();
        //2.指定连接的kafka集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092");
        //3.Ack应答级别
        properties.put(ProducerConfig.ACKS_CONFIG,"all");
        //4.重试次数
        properties.put("retries",1);
        //5.批次大小 16k
        properties.put("batch.size",16384);
        //6.等待时间
        properties.put("linger.ms",1);
        //7.RecordAccumulator缓冲区大小
        properties.put("buffer.memory",33554432);
        //8.Key,Value的序列化类
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        //添加分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.demo.kafka.partitioner.MyPartitioner");

        //9.创建生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        //10.发送数据
        for (int i = 0; i < 10; i++) {
            Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>("first", "atguigu", "atguigu--" + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println(metadata.partition() + "--" + metadata.offset());
                    } else {
                        exception.printStackTrace();
                    }
                }
            });
            //同步发送
            RecordMetadata recordMetadata = future.get();
        }
        //11.关闭资源
        producer.close();
    }
}

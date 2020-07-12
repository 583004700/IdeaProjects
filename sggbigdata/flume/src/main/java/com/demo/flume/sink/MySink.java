package com.demo.flume.sink;

import org.apache.flume.Channel;
import org.apache.flume.ChannelException;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySink extends AbstractSink implements Configurable {
    private String prefix;
    private String subfix;
    private Logger logger = LoggerFactory.getLogger(MySink.class);;
    @Override
    public void configure(Context context) {
        prefix = context.getString("prefix");
        subfix = context.getString("subfix", "atguigu");
    }

    /**
     * 1.获取Channel
     * 2.从Channel获取事务以及数据
     * 3.发送数据
     *
     * @return
     * @throws EventDeliveryException
     */
    @Override
    public Status process() throws EventDeliveryException {
        Status status = null;

        Channel channel = getChannel();
        Transaction transaction = channel.getTransaction();
        transaction.begin();
        try {
            Event event = channel.take();
            if(event != null){
                //处理事件
                String body = new String(event.getBody());
                logger.info(prefix+body+subfix);
            }
            transaction.commit();
            status = Status.READY;
        } catch (ChannelException e) {
            e.printStackTrace();
            transaction.rollback();
            status = Status.BACKOFF;
        } finally {
            transaction.close();
        }
        return status;
    }
}

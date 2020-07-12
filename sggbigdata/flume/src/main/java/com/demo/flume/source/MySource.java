package com.demo.flume.source;

import org.apache.flume.Context;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;

public class MySource extends AbstractSource implements Configurable, PollableSource {
    //定义全局的前缀和后缀
    private String prefix;
    private String subfix;

    @Override
    public void configure(Context context) {
        //读取配置信息，给前后缀赋值
        prefix = context.getString("prefix");
        subfix = context.getString("subfix","atguigu");
    }

    /**
     * 1.接收数据(for循环造数据)
     * 2.封装为事件
     * 3.将事件传给channel
     *
     * @return
     * @throws EventDeliveryException
     */
    @Override
    public Status process() throws EventDeliveryException {
        Status status = null;
        try {
            //1.接收数据
            for (int i = 0; i < 5; i++) {
                //2.构建事件对象
                SimpleEvent event = new SimpleEvent();
                //3.给事件设置值
                event.setBody((prefix+"--"+i+"--"+subfix).getBytes());
                //4.将事件传给channel
                getChannelProcessor().processEvent(event);
            }
            status = Status.READY;
        } catch (Exception e) {
            e.printStackTrace();
            status = Status.BACKOFF;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public long getBackOffSleepIncrement() {
        return 0;
    }

    @Override
    public long getMaxBackOffSleepInterval() {
        return 0;
    }
}

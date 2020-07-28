package com.demo.flume.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeInterceptor2 implements Interceptor {
    //声明一个存放事件的集合
    private List<Event> addHeaderEvents;

    @Override
    public void initialize() {
        addHeaderEvents = new ArrayList<Event>();
    }

    //单个事件拦截
    @Override
    public Event intercept(Event event) {
        //获取事件中的头信息
        Map<String,String> headers = event.getHeaders();
        //获取事件中的body
        String body = new String(event.getBody());
        //根据body中是否有"hello"来决定添加怎样的头信息
        //kafka Sink会根据不同topic值发送到不同的主题中
        if(body.contains("hello")){
            headers.put("topic","first");
        }else{
            headers.put("topic","second");
        }
        return event;
    }

    //批量事件拦截
    @Override
    public List<Event> intercept(List<Event> list) {

        //1.清空集合
        addHeaderEvents.clear();
        //遍历events，给每一个事件添加头信息
        for(Event event : list){
            addHeaderEvents.add(intercept(event));
        }

        return addHeaderEvents;
    }

    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new TypeInterceptor2();
        }

        @Override
        public void configure(Context context) {

        }
    }
}

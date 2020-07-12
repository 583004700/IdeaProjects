package com.demo.mydemo.jdk.server.tag;

import javax.xml.ws.Endpoint;

public class Publish {
    public static void main(String[] args) {
        Endpoint.publish("http://127.0.0.1:8080/hi",new HelloServiceImpl());
        System.out.println("服务发布成功");
    }
}

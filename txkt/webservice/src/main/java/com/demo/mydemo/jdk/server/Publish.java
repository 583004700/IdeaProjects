package com.demo.mydemo.jdk.server;

import javax.xml.ws.Endpoint;

public class Publish {
    public static void main(String[] args) {
        String address = "http://localhost:8080/hello";
        Endpoint.publish(address,new HelloServiceImpl());
        System.out.println("服务发布成功");
    }
}

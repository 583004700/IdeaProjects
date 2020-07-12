package com.demo.mydemo.jdk.client;

public class Invoke {
    public static void main(String[] args) throws Exception{
        //使用javahome/bin下面的工具在当前目录生成java代码
        //wsimport -p com.demo.mydemo.jdk.client -s . http://192.168.0.107:8080/hello?wsdl
        HelloServiceImpl helloService = new HelloServiceImplService().getHelloServiceImplPort();
        String str = helloService.sayHello("小明");
        System.out.println(str);
    }
}

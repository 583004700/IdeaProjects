package com.demo.mydemo.jdk.server.tag;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(endpointInterface = "com.demo.mydemo.jdk.server.tag.IHelloService",
        serviceName = "helloService",portName = "helloServicePort",targetNamespace = "http://abc.org")
public class HelloServiceImpl implements IHelloService{

    @Override
    public String sayHi(String name) {
        return name+"say you are do be";
    }

    @Override
    public String sayHi1(String name) {
        return "";
    }
}

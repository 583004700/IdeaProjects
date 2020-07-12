package com.demo.mydemo.cxf.server;

import javax.jws.WebService;

@WebService(endpointInterface = "com.demo.mydemo.cxf.server.IHelloService")
public class HelloServiceImpl implements IHelloService{
    @Override
    public Student sayX(String name) {
        Student student = new Student();
        student.setName(name+"say 小奶瓶！");
        return student;
    }
}

package com.demo.mydemo.jdk.server;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService
public class HelloServiceImpl {
    @WebMethod
    public String sayHello(String name){
        return name+"say hello!";
    }
    @WebMethod
    public List<User> sayHello2(List<User> list){
        List<User> users = new ArrayList<User>();
        User user = new User();
        user.setId(1);
        user.setName("张三");
        return users;
    }
}

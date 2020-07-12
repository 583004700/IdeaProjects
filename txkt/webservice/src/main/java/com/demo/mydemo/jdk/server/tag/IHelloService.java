package com.demo.mydemo.jdk.server.tag;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace = "http://abc.org")
@SOAPBinding(style= SOAPBinding.Style.RPC)
public interface IHelloService {
    @WebMethod
    @WebResult(name="ret") String sayHi(@WebParam(name="name") String name);
    //修改暴露的方法名及排除方法
    @WebMethod(operationName = "sayHello",exclude = true)
    String sayHi1(String name);
}

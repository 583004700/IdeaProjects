package com.demo.mydemo.cxf.server;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class Publish {
    public static void main(String[] args) {
        JaxWsServerFactoryBean factoryBean = new JaxWsServerFactoryBean();
        factoryBean.setAddress("http://localhost:8081/hihi");
        factoryBean.setServiceClass(IHelloService.class);
        factoryBean.setServiceBean(new HelloServiceImpl());
        //添加日志拦截器
//        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
//        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
//        factoryBean.getInInterceptors().add(new AuthInfoInInterceptor());
        factoryBean.create();
        System.out.println("发布服务成功");
    }
}

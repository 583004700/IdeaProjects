package com.demo.mydemo.cxf.client;

import com.demo.mydemo.cxf.server.Student;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class Invoke {
    public static void main(String[] args) {
        /*JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
        bean.setAddress("http://localhost:8081/hihi");
        bean.setServiceClass(IHelloService.class);

        //添加日志拦截器
        bean.getOutInterceptors().add(new LoggingOutInterceptor());
        bean.getInInterceptors().add(new LoggingInInterceptor());
        bean.getOutInterceptors().add(new AuthInfoOutInterceptor());

        IHelloService helloService = (IHelloService)bean.create();
        System.out.println(helloService.sayX("小东西"));*/
        main2();
    }

    public static void main2(){
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8081/hihi?wsdl");
        // 需要密码的情况需要加上用户名和密码
        client.getOutInterceptors().add(new LoggingOutInterceptor());
        client.getOutInterceptors().add(new LoggingInInterceptor());
        client.getOutInterceptors().add(new AuthInfoOutInterceptor());
        Object[] objects = new Object[0];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("sayX", "动态调用小东西");
            System.out.println("返回数据:" + objects[0]);
            System.out.println("返回数据类型:"+objects[0].getClass());
            Student student = (Student) objects[0];
            System.out.println(student.getName());
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }

    }
}

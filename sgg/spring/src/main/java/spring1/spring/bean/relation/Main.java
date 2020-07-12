package spring1.spring.bean.relation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring1.spring.bean.autowire.Address;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring1/beans-relation.xml");

//        Address address = (Address) ctx.getBean("address");
//        System.out.println(address);

        Address address = (Address) ctx.getBean("address2");
        System.out.println(address);
    }
}

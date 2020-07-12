package spring1.spring.bean.autowire;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring1/beans-autowire.xml");
        Person person = (Person) ctx.getBean("person");
        System.out.println(person);
    }
}

package spring1.spring.bean.properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring1/beans-properties.xml");
        DataSource dataSource = (DataSource)ctx.getBean("dataSource");
        System.out.println(dataSource);
    }
}

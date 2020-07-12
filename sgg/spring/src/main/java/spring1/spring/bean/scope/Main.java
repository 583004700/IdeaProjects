package spring1.spring.bean.scope;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring1.spring.bean.Car;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring1/beans-scope.xml");
        Car car = (Car) ctx.getBean("car");
        System.out.println(car);
        Car car1 = (Car) ctx.getBean("car");
        System.out.println(car == car1);

    }
}

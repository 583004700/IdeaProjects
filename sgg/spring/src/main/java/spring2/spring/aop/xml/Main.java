package spring2.spring.aop.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring2/applicationContext-xml.xml");

        ArithmeticCalculator arithmeticCalculator = ctx.getBean(ArithmeticCalculator.class);

        System.out.println(arithmeticCalculator.add(1,4));

        System.out.println(arithmeticCalculator.div(10,2));
    }
}

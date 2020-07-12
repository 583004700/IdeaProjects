package spring1.spring.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring1.spring.bean.collections.DataSource;

//匹配java块级注释  \/\*([^\*^\/]*|[\*^\/*]*|[^\**\/]*)*\*\/

public class Main {
    public static void main(String[] args) {
//        HelloWorld helloWorld = new HelloWorld();
//        helloWorld.setName("zhuwb");

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring1/applicationContext.xml");
        HelloWorld helloWorld = (HelloWorld) ctx.getBean("helloWorld");
        Object o = ctx.getBean("environment");
        System.out.println("prepareBeanFactory 方法会注册 environment----------"+o);

        helloWorld.hello();

        HelloWorld helloWorld1 = ctx.getBean(HelloWorld.class);

        System.out.println(helloWorld == helloWorld1);

        Car car = (Car) ctx.getBean("car");
        System.out.println(car);

        Car car2 = (Car)ctx.getBean("car2");
        System.out.println(car2);

        Person person = (Person)ctx.getBean("person");
        System.out.println(person);

        spring1.spring.bean.collections.Person person2 = (spring1.spring.bean.collections.Person)ctx.getBean("person2");
        System.out.println(person2);

        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource);

        spring1.spring.bean.collections.Person person3 = (spring1.spring.bean.collections.Person)ctx.getBean("person3");
        System.out.println(person3);

        System.out.println(System.getProperties());

        System.out.println(System.getenv());
    }
}

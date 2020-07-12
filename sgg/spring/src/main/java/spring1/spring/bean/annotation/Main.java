package spring1.spring.bean.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring1/beans-annotation.xml");
        TestObject to = (TestObject) ctx.getBean("testObject");
        System.out.println(to);

        UserRepository userRepository = (UserRepository) ctx.getBean("userRepository");
        System.out.println(userRepository);

        UserService userService = (UserService) ctx.getBean(UserService.class);
        userService.add();
    }
}

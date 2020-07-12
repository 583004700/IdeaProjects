package spring1.spring.bean.generic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 泛型依赖注入
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring1/beans-generic.xml");
        BaseService userService = (UserService) ctx.getBean("userService");
        userService.add();

        BaseService carService = (CarService) ctx.getBean("carService");
        carService.add();
    }

}

package com.atguigu;

import com.atguigu.bean.Person;
import com.atguigu.config.MainConfig;
import com.atguigu.config.SuperClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {
    public static void main(String[] args) {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
//        Person bean = (Person) applicationContext.getBean("person");
//        System.out.println(bean);

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
//        Person person = applicationContext.getBean(Person.class);
//        System.out.println(person);
//
//        String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
//        for(String name : namesForType){
//            System.out.println(name);
//        }

        SuperClass superClass = applicationContext.getBean("superClass", SuperClass.class);
        System.out.println(superClass.getName());
    }
}

package com.atguigu.test;

import com.atguigu.bean.Person;
import com.atguigu.config.MainConfig;
import com.atguigu.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

public class IOCTest {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

    @Test
    public void test01(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        printBeans(applicationContext);
    }

    @Test
    public void test02(){
        printBeans(applicationContext);

        Object bean = applicationContext.getBean("person");
        Object bean2 = applicationContext.getBean("person");
        System.out.println(bean == bean2);
    }

    @Test
    public void test03(){
        Environment environment = applicationContext.getEnvironment();
        //获取环境变量的值
        String property = environment.getProperty("os.name");
        System.out.println(property);
        //Windows 10

        printBeans(applicationContext);

        Map<String,Person> persons = applicationContext.getBeansOfType(Person.class);
        System.out.println(persons);
    }

    private void printBeans(ApplicationContext context){
        String[] definitionNames = context.getBeanDefinitionNames();
        for(String name : definitionNames){
            System.out.println(name);
        }
    }

    @Test
    public void testImport(){
        printBeans(applicationContext);
        Object yellow = applicationContext.getBean("com.atguigu.bean.Yellow");
        System.out.println(yellow);

        //获取到的是调用colorFactoryBean getObject返回的对象
        Object bean2 = applicationContext.getBean("colorFactoryBean");
        System.out.println(bean2.getClass());

        //获取到的是调用colorFactoryBean
        Object bean3 = applicationContext.getBean("&colorFactoryBean");
        System.out.println(bean3.getClass());
    }
}

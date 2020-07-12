package com.atguigu.test;

import com.atguigu.bean.Boss;
import com.atguigu.bean.Color;
import com.atguigu.config.MainConfigOfAutowired;
import com.atguigu.config.MainConfigOfPropertyValues;
import com.atguigu.dao.BookDao;
import com.atguigu.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class IOCTest_Autowired {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);

    private void printBeans(ApplicationContext context){
        String[] definitionNames = context.getBeanDefinitionNames();
        for(String name : definitionNames){
            System.out.println(name);
        }
    }

    @Test
    public void test01(){
        printBeans(applicationContext);
        BookService bookService = applicationContext.getBean(BookService.class);
        System.out.println(bookService);
        //BookDao bookDao = applicationContext.getBean(BookDao.class);
        //System.out.println(bookDao);
        Boss boss = applicationContext.getBean(Boss.class);
        System.out.println(boss);

        Color color = applicationContext.getBean(Color.class);
        System.out.println(color);
        applicationContext.close();
    }
}

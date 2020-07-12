package com.atguigu.test;

import com.atguigu.config.MainConfigOfProfile;
import com.atguigu.config.MainConfigOfPropertyValues;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class IOCTest_Profile {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    private void printBeans(ApplicationContext context){
        String[] definitionNames = context.getBeanDefinitionNames();
        for(String name : definitionNames){
            System.out.println(name);
        }
    }

    @Test
    public void test01(){
        //使用代码方式激活某种环境
        applicationContext.getEnvironment().setActiveProfiles("test","dev");
        applicationContext.register(MainConfigOfProfile.class);

        applicationContext.refresh();

        printBeans(applicationContext);

        applicationContext.close();
    }
}

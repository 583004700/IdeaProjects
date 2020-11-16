package com.atguigu.config;

import org.springframework.context.annotation.Bean;

public class SuperClassConfig {
    @Bean
    public SuperClass superClass(){
        SuperClass superClass = new SuperClass();
        superClass.setName("zhangsan");
        return superClass;
    }
}

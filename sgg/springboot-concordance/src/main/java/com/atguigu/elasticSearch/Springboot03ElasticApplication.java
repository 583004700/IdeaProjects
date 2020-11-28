package com.atguigu.elasticSearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot默认支持两种技术来和ES交互
 * 1、Jest
 * 2、SpringData ElasticSearch (maven中引入的es版本可能不对)
 *
 */
@SpringBootApplication
public class Springboot03ElasticApplication {
    public static void main(String[] args) {
        SpringApplication.run(Springboot03ElasticApplication.class,args);
    }
}

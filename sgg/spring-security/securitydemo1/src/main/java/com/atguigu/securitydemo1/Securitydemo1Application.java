package com.atguigu.securitydemo1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/*
    默认用户名：user
    密码在控制台会打印出来
 */
@SpringBootApplication
@MapperScan("com.atguigu.securitydemo1.mapper")

@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class Securitydemo1Application {
    public static void main(String[] args) {
        SpringApplication.run(Securitydemo1Application.class, args);
    }
}

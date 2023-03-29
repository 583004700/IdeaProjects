package com.demo.mydemo.fund.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.demo.mydemo.fund.mapper")
public class MybatisPlusConfig {
}

package com.atguigu.config;

import com.atguigu.bean.Person;
import com.atguigu.service.BookService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Configurable  //告诉spring这是一个配置类
@ComponentScans(
        value={
                @ComponentScan(value="com.atguigu.ext",includeFilters = {
//                        @ComponentScan.Filter(type= FilterType.ANNOTATION,
//                                classes = {Controller.class, Service.class}),
//                        @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,
//                                classes = BookService.class),
                        @ComponentScan.Filter(type=FilterType.CUSTOM,classes = MyTypeFilter.class)},
                        useDefaultFilters = false)
        }
)
//@ComponentScan(value="com.atguigu",includeFilters = {@ComponentScan.Filter(type= FilterType.ANNOTATION,classes = {Controller.class, Service.class})},useDefaultFilters = false)
//FilterType.ANNOTATION:按照注解
//FilterType.ASSIGNABLE_TYPE:按照给定的类型
//FilterType.ASPECTJ:使用ASPECTJ表达式
//FilterType.REGEX:使用正则表达式
//FilterType.CUSTOM:使用自定义规则
public class MainConfig {

    //给容器中注册一个Bean;类型为返回值的类型，id默认是用方法名作为id
    @Bean("person")
    public Person person01(){
        return new Person("lisi",20);
    }
}

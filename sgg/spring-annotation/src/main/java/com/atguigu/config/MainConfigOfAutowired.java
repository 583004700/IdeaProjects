package com.atguigu.config;

import com.atguigu.bean.Car;
import com.atguigu.bean.Color;
import com.atguigu.dao.BookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * 自动装配；
 *      Spring利用依赖注入，完成对IOC容器中各个组件的依赖关系赋值
 * 1.@Autowired 自动注入
 *      默认优先按照类型去容器中找对应的组件，如果找到多个相同类型的组件，再将属性名称作为组件的id去容器中查找
 *      使用@Primary:让Spring进行自动装配的时候，默认使用首选的bean
 * 2.Spring还支持使用@Resource(JSR250)和@Inject(JSR330)【java规范的注解】
 *      @Resource 默认按照组件名称进行装配,没有能支持@Primary功能，也没有支持@Autowired(required = false)的功能
 *      @Inject 需要导入javax.inject的包，可以支持@Primary功能，但不能支持required = false
 * 3.自定义组件想要使用Spring容器底层的一些组件(ApplicationContext,BeanFactory)
 *      自定义组件实现xxxAware接口；在创建对象的时候，会调用接口规定的方法注入相关组件，Aware
 */
@Configuration
@ComponentScan({"com.atguigu.service","com.atguigu.dao","com.atguigu.controller","com.atguigu.bean"})
public class MainConfigOfAutowired {

    //Primary使Autowired首选装配此bean
    @Primary
    @Bean("bookDao2")
    public BookDao bookDao(){
        return new BookDao();
    }

    /**
     * Bean标注的方法创建对象的时候，方法参数的值从容器中获取
     * @param car
     * @return
     */
    @Bean
    public Color color(Car car){
        Color color = new Color();
        color.setCar(car);
        return color;
    }
}

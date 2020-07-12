package com.atguigu.config;

import com.atguigu.bean.Car;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * bean的生命周期
 *      bean的创建---初始化---销毁的过程
 * 容器管理bean的生命周期
 * 多实例的bean容器不会销毁
 * 1.指定初始化和销毁方法
 * 2.通过让bean实现InitializingBean(定义初始化逻辑)，DisposableBean(定义销毁)
 * 3.可以使用JSR250;
 *      @PostConstruct:在bean创建完成并且属性赋值完成，来执行初始化方法
 *      @PreDestroy:在容器销毁bean之前执行
 * 4.BeanPostProcessor bean的后置处理器：
 *      在bean初始化前后进行一些处理工作；
 *      postProcessBeforeInitialization
 *      postProcessAfterInitialization
 *
 * Spring底层对BeanPostProcessor的使用
 *      bean赋值，注入其它组件，@Autowired，生命周期注解功能，@Async等
 *
 */
@ComponentScan("com.atguigu.bean")
@Configurable
public class MainConfigOfLifeCycle {
    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car car(){
        return new Car();
    }
}

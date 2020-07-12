package com.atguigu.config;

import com.atguigu.bean.Color;
import com.atguigu.bean.ColorFactoryBean;
import com.atguigu.bean.Person;
import com.atguigu.bean.Red;
import com.atguigu.condition.LinuxCondition;
import com.atguigu.condition.MyImportBeanDefinitionRegistrar;
import com.atguigu.condition.MyImportSelector;
import com.atguigu.condition.WindowsCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration
//导入组件，id默认是全类名
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig2 {

    //默认是单实例的

    /**
     * prototype:多实例的，ioc启动时不会创建对象，获取的时候才创建对象
     * singleton:单实例的（默认值）,ioc容器启动会调用方法创建对象放到ioc容器中
     * request:同一次请求创建一个实例
     * session:同一个session创建一个实例
     * @return
     */
    @Lazy
//    @Scope("prototype")
    @Bean("person")
    public Person person(){
        System.out.println("给容器中添加Person...");
        return new Person("张三",25);
    }

    /**
     * @Conditional:按照一定的条件进行判断，满足条件才给容器中注册bean
     * 如果系统是windows，给容器中注册（"bill"）
     * 如果是linux系统，给容器中注册("linus")
     * Conditional注解也可以标注在类上
     */
    @Conditional({WindowsCondition.class})
    @Bean
    public Person person01(){
        return new Person("Bill Gates",62);
    }
    @Conditional({LinuxCondition.class})
    @Bean
    public Person person02(){
        return new Person("linus",48);
    }

    /**
     * 给容器中注册组件：
     * 1.包扫描+组件标注注解（@Controller/@Service/@Repository/@Component[自己写的类]
     * 2.@Bean[导入第三方里面的组件]
     * 3.@Import[快速给容器中导入一个组件]
     *      1.@Import(要导入到容器中的组件)；容器中应付自动注册这个组件，id默认是全类名
     *      2.ImportSelector：返回需要导入组件的全类名数组
     *      3.ImportBeanDefinitionRegistrar:
     * 4.使用Spring提供的FactoryBean(工厂Bean)
     *      1.默认获取到的是工厂bean调用getObject创建的对象
     *      2.要获取工厂bean本身，需要在id前面加一个&前缀
     */
    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }

}

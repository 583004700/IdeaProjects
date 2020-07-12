package s02config.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import s02config.service.HelloService;

@Configuration
public class MyAppConfig {

    @Bean
    public HelloService helloService(){
        System.out.println("配置类@Bean给容器中添加组件");
        return new HelloService();
    }
}

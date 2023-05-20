package s02config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

//@ImportResource(locations = {"classpath:s02config/beans.xml"})
//@SpringBootApplication
public class SpringBoot02ConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBoot02ConfigApplication.class,args);
    }
}

package s02config.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
/**
 * ConfigurationProperties将默认配置文件中的值映射到这个组件中
 * PropertySource可以指定加载某个配置文件中的内容
 */
@ConfigurationProperties(prefix = "person")
@PropertySource(value = "classpath:s02config/person.properties")
@Component
//@Validated
public class Person {
    @Value("${person.last-name}")
//    @Email
    private String lastName;
    @Value("#{11*2}")
    private Integer age;
    @Value("true")
    private Boolean boss;
    private Date birth;

//    @Value("person.maps")
    private Map<String,Object> maps;
    private List<Object> lists;
    private Dog dog;
}

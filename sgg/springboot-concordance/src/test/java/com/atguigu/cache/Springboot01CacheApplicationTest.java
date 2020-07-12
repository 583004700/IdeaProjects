package com.atguigu.cache;

import com.atguigu.cache.bean.Employee;
import com.atguigu.cache.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class Springboot01CacheApplicationTest {
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;    //操作k-v字符串的
    @Autowired
    RedisTemplate redisTemplate;    //k-v都是对象的
    @Autowired
    RedisTemplate<Object,Employee> empRedisTemplate;

    /**
     * Redis常见的五大数据类型
     *  String（字符串）、List（列表）、Set（集合）、Hash（散列）、ZSet（有序集合）
     *  stringRedisTemplate.opsForValue()[String（字符串）]
     *  stringRedisTemplate.opsForList()[List（列表）]
     *  stringRedisTemplate.opsForSet()[Set（集合）]
     *  stringRedisTemplate.opsForHash()[Hash（散列）]
     *  stringRedisTemplate.opsForZSet()[ZSet（有序集合）]
     */
    @Test
    public void test01(){
        //stringRedisTemplate.opsForValue().append("mgk","李四");
//        String mgk = stringRedisTemplate.opsForValue().get("mgk");
//        System.out.println(mgk);
        stringRedisTemplate.opsForList().leftPush("mylist","2");
    }

    @Test
    public void test02(){
        //Employee employee = employeeMapper.getEmpById(2);
        //redisTemplate.opsForValue().set("emp-01",employee);

//        Employee employee = (Employee) redisTemplate.opsForValue().get("emp-01");
        //默认保存对象使用jck的序列化机制，序列化后的数据保存到redis中
//        System.out.println(employee);

        Employee employee = employeeMapper.getEmpById(2);
        //使用自己定义的redisTemplate
        empRedisTemplate.opsForValue().set("emp-03",employee);
    }

    @Test
    public void contextLoads(){
        Employee empById = employeeMapper.getEmpById(1);
        System.out.println(empById);
    }
}

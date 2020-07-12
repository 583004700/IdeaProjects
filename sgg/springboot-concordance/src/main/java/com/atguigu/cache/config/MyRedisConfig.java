package com.atguigu.cache.config;

import com.atguigu.cache.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class MyRedisConfig {
    @Bean
    public RedisTemplate<Object, Employee> empRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object,Employee> template = new RedisTemplate<Object,Employee>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Employee> ser = new Jackson2JsonRedisSerializer<Employee>(Employee.class);
        template.setDefaultSerializer(ser);
        return template;
    }

    /**
     * 配置后默认的CacheManager不会再自动注入进去了
     * @param empRedisTemplate
     * @return
     */
    @Bean
    public RedisCacheManager empCacheManager(@Autowired @Qualifier("empRedisTemplate") RedisTemplate<Object,Employee> empRedisTemplate){
        RedisCacheManager cacheManager = new RedisCacheManager(empRedisTemplate);
        //key使用前缀
        //cacheManager.setUsePrefix(true);
        return cacheManager;
    }

    @Primary
    @Bean
    public RedisCacheManager redisCacheManager(@Autowired @Qualifier("redisTemplate") RedisTemplate<Object,Object> redisTemplate){
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        //key使用前缀
        //cacheManager.setUsePrefix(true);
        return cacheManager;
    }
}

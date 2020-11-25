package com.demo.mydemo.test.mp5;

import com.demo.mydemo.mp5.beans.User;
import com.demo.mydemo.mp5.mapper.EmployeeMapper;
import com.demo.mydemo.mp5.mapper.UserMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMP {
    private ApplicationContext ioc = new ClassPathXmlApplicationContext("mp5/applicationContext.xml");

    private EmployeeMapper employeeMapper = ioc.getBean("employeeMapper", EmployeeMapper.class);

    private UserMapper userMapper = ioc.getBean("userMapper", UserMapper.class);

    /**
     * 测试公共字段填充
     */
    @Test
    public void testMetaObjectHandler() {
        User user = new User();
        user.setLogicFlag(1);
        userMapper.insert(user);
    }

    /**
     * 测试逻辑删除
     */
    @Test
    public void testLogicDelete() {
        Integer result = userMapper.deleteById(1);
        System.out.println(result);
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

    /**
     * 测试自定义全局操作
     */
    @Test
    public void testMySqlInjector() {
        Integer result = employeeMapper.deleteAll();
        System.out.println("result:" + result);
    }
}

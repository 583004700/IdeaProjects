package com.demo.mydemo.test.mp5;

import com.baomidou.mybatisplus.plugins.Page;
import com.demo.mydemo.mp5.beans.Employee;
import com.demo.mydemo.mp5.mapper.EmployeeMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestMP {
    private ApplicationContext ioc = new ClassPathXmlApplicationContext("mp5/applicationContext.xml");

    private EmployeeMapper employeeMapper = ioc.getBean("employeeMapper", EmployeeMapper.class);

}

package com.demo.mydemo.test.mp4;

import com.baomidou.mybatisplus.plugins.Page;
import com.demo.mydemo.mp4.beans.Employee;
import com.demo.mydemo.mp4.mapper.EmployeeMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestMP {
    private ApplicationContext ioc = new ClassPathXmlApplicationContext("mp4/applicationContext.xml");

    private EmployeeMapper employeeMapper = ioc.getBean("employeeMapper", EmployeeMapper.class);

    /**
     * 测试乐观锁插件
     */
    @Test
    public void testOptimisticLocker() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setLastName("张三");
        employee.setEmail("zhangsan@qq.com")
                .setGender(0)
                .setAge(18);
        employee.setVersion(1);
        employeeMapper.updateById(employee);
    }

    /**
     * 测试性能分析插件
     */
    @Test
    public void testPerformance() {
        Employee employee = new Employee();
        employee.setLastName("张三");
        employee.setEmail("zhangsan@qq.com")
                .setGender(0)
                .setAge(18);

        employeeMapper.insert(employee);
    }

    /**
     * 测试分析插件
     */
    @Test
    public void testSQLExplain() {
        int result = employeeMapper.delete(null);
        System.out.println(result);
    }

    /**
     * 测试分页插件
     */
    @Test
    public void testPage() {
        Page<Employee> page = new Page<Employee>(1, 2);

        List<Employee> employees = employeeMapper.selectPage(page, null);
        System.out.println(employees);
        System.out.println("----------获取分页相关的一些信息----------");
        System.out.println("总条数:" + page.getTotal());
        System.out.println("当前页码：" + page.getCurrent());
        System.out.println("总页码：" + page.getPages());
        System.out.println("每页显示的条数:" + page.getSize());
        System.out.println("是否有上一页:" + page.hasPrevious());
        System.out.println("是否有下一页：" + page.hasNext());
        //将查询的结果封装到page对象中
        page.setRecords(employees);
        System.out.println(page.getRecords());
    }
}

package com.demo.mydemo.test.mp2;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.demo.mydemo.mp2.beans.Employee;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestMP {
    private ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");

    /**
     * AR 分页复杂操作
     */
    @Test
    public void testARPage() {
        Employee employee = new Employee();
        Page<Employee> page = employee.selectPage(new Page<Employee>(1, 1),
                new EntityWrapper<Employee>().like("last_name", "小"));
        System.out.println(page.getRecords());
    }

    /**
     * AR 删除操作
     */
    @Test
    public void testARDelete() {
//        Employee employee = new Employee();
//        employee.setId(2);
//        //如果删除不存在的数据，返回的结果依然是true
//        employee.deleteById();

        Employee employee = new Employee();
        boolean result = employee.delete(new EntityWrapper<Employee>().like("last_name", "小"));
        System.out.println(result);
    }

    /**
     * AR 查询操作
     */

    @Test
    public void testARSelect() {
//        Employee employee = new Employee();
//        employee.setId(13);
//        //Employee result = employee.selectById(13);
//        Employee result = employee.selectById();
//        System.out.println(result);

//        Employee employee = new Employee();
//        employee.selectAll();

//        Employee employee = new Employee();
//        List<Employee> result = employee.selectList(new EntityWrapper<Employee>().like("last_name", "老师"));
//        System.out.println(result);

        Employee employee = new Employee();
        int result = employee.selectCount(new EntityWrapper<Employee>().eq("gender", 0));
        System.out.println(result);
    }

    /**
     * AR 修改操作
     */
    @Test
    public void testARUpdate() {
        Employee employee = new Employee();
        employee.setId(13);
        employee.setLastName("ARId");
        boolean result = employee.updateById();
        System.out.println(result);
    }

    /**
     * AR 插入操作
     */

    @Test
    public void testARInsert() {
        Employee employee = new Employee();
        employee.setLastName("AR");
        employee.setEmail("Ar@qq.com");
        employee.setAge(12);
        employee.setGender(1);
        boolean result = employee.insert();
        System.out.println(result);
    }

}

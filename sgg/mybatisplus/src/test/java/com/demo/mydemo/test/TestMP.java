package com.demo.mydemo.test;

import com.baomidou.mybatisplus.plugins.Page;
import com.demo.mydemo.beans.Employee;
import com.demo.mydemo.mapper.EmployeeMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class TestMP {
    private ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");

    private EmployeeMapper employeeMapper = ioc.getBean("employeeMapper",EmployeeMapper.class);

    @Test
    public void testCommonDelete(){
//        int result = employeeMapper.deleteById(4);
//        System.out.println(result);

//        Map<String,Object> columnMap = new HashMap<String,Object>();
//        columnMap.put("last_name","MP");
//        int result = employeeMapper.deleteByMap(columnMap);
//        System.out.println(result);

        int result = employeeMapper.deleteBatchIds(Arrays.asList("11","12"));
        System.out.println(result);
    }

    @Test
    public void testCommonSelect(){
        //1.通过id查询
        //Employee employee = employeeMapper.selectById(9);
        //System.out.println(employee);

        //2.通过多个列进行查询,id和lastName
//        Employee employee = new Employee();
//        employee.setId(7).setLastName("MP");
//        Employee result = employeeMapper.selectOne(employee);
//        System.out.println(result);

        //3.通过多个id进行查询
//        List<Integer> idList = new ArrayList<Integer>();
//        idList.add(4);
//        idList.add(5);
//        idList.add(6);
//        List<Employee> result = employeeMapper.selectBatchIds(idList);
//        System.out.println(result);

        //4.通过map封装条件查询,map中key为列名，不是属性名
//        Map<String,Object> columnMap = new HashMap<String,Object>();
//        columnMap.put("last_name","MP");
//        List<Employee> result = employeeMapper.selectByMap(columnMap);
//        System.out.println(result);

        //5.分页查询,只是内存分页
        List<Employee> result = employeeMapper.selectPage(new Page(2,2),null);
        System.out.println(result);
    }

    @Test
    public void testCommonUpdate(){
        Employee employee = new Employee();
        employee.setId(10).setAge(100)
                .setEmail("mybatisPlus@sina.com")
                .setGender(0);
        //修改值不为null的字段
        int result = employeeMapper.updateById(employee);
        //修改所有列，包括值为null的字段
        //int result = employeeMapper.updateAllColumnById(employee);
        System.out.println(result);
    }

    @Test
    public void testCommonInsert(){
        Employee employee = new Employee();
        employee.setLastName("MP").setEmail("mp@qq.com")
        .setAge(22).setGender(1).setSalary(20000d);
        //insert方法插入时，null值字段不会出现在sql语句中
        //int result = employeeMapper.insert(employee);
        //insertAllColumn插入时，null值的字段也会显示的插入null
        int result = employeeMapper.insertAllColumn(employee);
        System.out.println(result);

        //获取当前数据在数据库中的主键值
        Integer key = employee.getId();
        System.out.println("key:"+key);
    }

    @Test
    public void testDataSource() throws SQLException {
        DataSource ds = ioc.getBean("dataSource",DataSource.class);
        Connection connection = ds.getConnection();
        System.out.println(connection);
    }
}

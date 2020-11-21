package com.demo.mydemo.test.mp1;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.demo.mydemo.mp1.beans.Employee;
import com.demo.mydemo.mp1.mapper.EmployeeMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class TestMP {
    private ApplicationContext ioc = new ClassPathXmlApplicationContext("mp1/applicationContext.xml");

    private EmployeeMapper employeeMapper = ioc.getBean("employeeMapper", EmployeeMapper.class);

    /**
     * 条件构造器 删除操作
     */
    @Test
    public void testEntityWrapperDelete() {
        employeeMapper.delete(
                new EntityWrapper<Employee>().eq("last_name", "Tom").eq("age", 22)
        );
    }

    /**
     * 条件构造器 修改操作
     */
    @Test
    public void testEntityWrapperUpdate() {
        Employee employee = new Employee();
        employee.setLastName("李老师");
        employee.setEmail("aaa@sina.com");
        employee.setGender(0);
        int result = employeeMapper.update(employee, new EntityWrapper<Employee>()
                .eq("last_name", "Tom").eq("age", 44));
        System.out.println(result);
    }

    /**
     * 条件构造器  查询操作
     */
    @Test
    public void testEntityWrapperSelect() {
        //分页查询tbl_employee表中，年龄在18-50之间，性别为男且姓名为Tom的所有用户
//        List<Employee> result = employeeMapper.selectPage(new Page<Employee>(1, 2),
//                new EntityWrapper<Employee>().between("age", 18, 50)
//                        .eq("gender",1).eq("last_name","Tom"));
//        System.out.println(result);

        //查询tbl_employee表中性别为女并且名称中带有老师或者邮箱中带有a的
//        employeeMapper.selectList(new EntityWrapper<Employee>().eq("gender", 0)
//                .like("last_name", "老师").orNew().like("email", "a"));

        //查询性别为女的，根据age进行排序（asc/desc）,简单分页

//        employeeMapper.selectList(new EntityWrapper<Employee>()
//        .eq("gender",0)
//                .orderBy("age").last("desc"));
        //.orderDesc(Arrays.asList("age")));

        List<Employee> emps = employeeMapper.selectPage(new Page<Employee>(1, 2),
                Condition.create().between("age", 18, 50)
                        .eq("gender", 1)
                        .eq("last_name", "Tom"));
    }

    @Test
    public void testCommonDelete() {
//        int result = employeeMapper.deleteById(4);
//        System.out.println(result);

//        Map<String,Object> columnMap = new HashMap<String,Object>();
//        columnMap.put("last_name","MP");
//        int result = employeeMapper.deleteByMap(columnMap);
//        System.out.println(result);

        int result = employeeMapper.deleteBatchIds(Arrays.asList("11", "12"));
        System.out.println(result);
    }

    @Test
    public void testCommonSelect() {
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
        List<Employee> result = employeeMapper.selectPage(new Page(2, 2), null);
        System.out.println(result);
    }

    @Test
    public void testCommonUpdate() {
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
    public void testCommonInsert() {
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
        System.out.println("key:" + key);
    }

    @Test
    public void testDataSource() throws SQLException {
        DataSource ds = ioc.getBean("dataSource", DataSource.class);
        Connection connection = ds.getConnection();
        System.out.println(connection);
    }
}

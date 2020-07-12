package spring3.spring.jdbc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JDBCTest {

    private ApplicationContext ctx = null;
    private JdbcTemplate jdbcTemplate;
    private EmployeeDao employeeDao;
    private DepartmentDao departmentDao;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    {
        ctx = new ClassPathXmlApplicationContext("spring3/applicationContext.xml");
        jdbcTemplate = (JdbcTemplate)ctx.getBean("jdbcTemplate");
        employeeDao = ctx.getBean(EmployeeDao.class);
        departmentDao = ctx.getBean(DepartmentDao.class);
        namedParameterJdbcTemplate = ctx.getBean(NamedParameterJdbcTemplate.class);
    }

    @Test
    public void testNamedParameterJdbcTemplate2(){
        String sql = "insert into employees(last_name,email,dept_id) values(:lastName,:email,:deptId)";
        Employee employee = new Employee();
        employee.setLastName("XYZ");
        employee.setEmail("xyz@sina.com");
        employee.setDeptId("2");

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(employee);
        namedParameterJdbcTemplate.update(sql,parameterSource);
    }

    /**
     * 可以为参数起名字
     */
    @Test
    public void testNamedParameterJdbcTemplate(){
        String sql = "insert into employees(last_name,email,dept_id) values(:ln,:email,:deptid)";
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("ln","FF");
        paramMap.put("email","89@qq.com");
        paramMap.put("deptid",2);
        namedParameterJdbcTemplate.update(sql,paramMap);
    }

    @Test
    public void testDepartmentDao(){
        System.out.println(departmentDao.get(1));
    }

    @Test
    public void testEmployeeDao(){
        System.out.println(employeeDao.get(1));
    }

    /**
     * 获取单个列的值，或做统计查询
     */
    @Test
    public void testQueryForObject2(){
        String sql = "select count(id) from employees";
        long count = jdbcTemplate.queryForObject(sql,Long.class);
        System.out.println(count);
    }

    @Test
    public void testQueryForList(){
        String sql = "select id,last_name lastName,email from employees where id > ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<Employee>(Employee.class);
        List<Employee> employees = jdbcTemplate.query(sql,rowMapper,1);
        System.out.println(employees);
    }

    /**
     * rowMapper指定如何去映射结果集
     */
    @Test
    public void testQueryForObject(){
        String sql = "select id,last_name lastName,email,dept_id from employees where id = ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<Employee>(Employee.class);
        Employee employee = jdbcTemplate.queryForObject(sql,rowMapper,1);
        System.out.println(employee);
    }

    /**
     * 执行批量操作
     */
    @Test
    public void testBatchUpdate(){
        String sql = "insert into employees(last_name,email,dept_id) values(?,?,?)";
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        batchArgs.add(new Object[]{"AA","aa",1});
        batchArgs.add(new Object[]{"BB","bb",2});
        batchArgs.add(new Object[]{"CC","cc",3});
        jdbcTemplate.batchUpdate(sql,batchArgs);
    }

    /**
     * 执行insert,update,delete
     */
    @Test
    public void testUpdate(){
        String sql = "update employees set last_name = ? where id = ?";
        jdbcTemplate.update(sql,"java","1");
    }

    @Test
    public void test() throws SQLException {
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }
}

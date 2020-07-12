package mybatis3.mapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class MybatisPlusTest {
    SqlSessionFactory sqlSessionFactory;
    @Before
    public void init() throws Exception{
        String resource = "mybatis3/mapper/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //此时的sqlSessionFactory是DefaultSqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testGetEmpById(){
        SqlSession session = sqlSessionFactory.openSession();
        EmployeeMapperPlus mapperPlus = session.getMapper(EmployeeMapperPlus.class);
        Employee employee = mapperPlus.getEmpById(1);
        System.out.println(employee.getLastName());
        System.out.println(employee.getEmail());
    }

    @Test
    public void testGetEmpAndDept(){
        SqlSession session = sqlSessionFactory.openSession();
        EmployeeMapperPlus mapperPlus = session.getMapper(EmployeeMapperPlus.class);
        Employee employee = mapperPlus.getEmpAndDept(1);
        System.out.println(employee.getDepartment().getDepartmentName());
    }

    /**
     * 全局配置中开启懒加载之后，查询员工时如果没有查询部门信息，则只发一条sql语句
     */
    @Test
    public void testGetEmpByIdStep(){
        SqlSession session = sqlSessionFactory.openSession();
        EmployeeMapperPlus mapperPlus = session.getMapper(EmployeeMapperPlus.class);
        Employee employee = mapperPlus.getEmpByIdStep(1);
        //System.out.println(employee.getDepartment().getDepartmentName());
        //System.out.println(employee.getDepartment().getId());
    }

    /**
     * 查询员工列表时，每个员工会单独发一条sql去查询部门信息，n+1的问题
     */
    @Test
    public void testGetEmpStep(){
        SqlSession session = sqlSessionFactory.openSession();
        EmployeeMapperPlus mapperPlus = session.getMapper(EmployeeMapperPlus.class);
        List<Employee> emps = mapperPlus.getEmpStep();

    }

    @Test
    public void testGetDeptByIdPlus(){
        SqlSession session = sqlSessionFactory.openSession();
        DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
        mapper.getDeptByIdPlus(1);
    }

    @Test
    public void testGetDeptListPlus(){
        SqlSession session = sqlSessionFactory.openSession();
        DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
        List<Department> departments = mapper.getDeptListPlus();
    }

    @Test
    public void testGetDeptByIdStep(){
        SqlSession session = sqlSessionFactory.openSession();
        DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
        Department department = mapper.getDeptByIdStep(1);
        System.out.println(department.getDepartmentName());
    }

    @Test
    public void testMyEmpDis(){
        SqlSession session = sqlSessionFactory.openSession();
        EmployeeMapperPlus mapper = session.getMapper(EmployeeMapperPlus.class);
        Employee employee = mapper.myEmpDis(4);
        System.out.println(employee);
    }

}

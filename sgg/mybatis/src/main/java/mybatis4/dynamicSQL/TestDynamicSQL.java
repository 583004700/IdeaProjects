package mybatis4.dynamicSQL;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDynamicSQL {
    SqlSessionFactory sqlSessionFactory;
    @Before
    public void init() throws Exception{
        String resource = "mybatis4/dynamicSQL/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //此时的sqlSessionFactory是DefaultSqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testGetEmpsByConditionIf(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee();
        employee.setLastName("小");
        List<Employee> list = mapper.getEmpsByConditionIf(employee);
        //list.forEach((o)->System.out.println(o.getId()));
    }

    @Test
    public void testGetEmpsByConditionTrim(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee();
        employee.setEmail("48");
        List<Employee> list = mapper.getEmpsByConditionTrim(employee);
        //list.forEach((o)->System.out.println(o.getId()));
    }

    @Test
    public void testGetEmpsByConditionChoose(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee();
        employee.setId(1);
        List<Employee> list = mapper.getEmpsByConditionChoose(employee);
        //list.forEach((o)->System.out.println(o.getId()));
    }

    @Test
    public void testUpdateEmp(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee();
        employee.setGender("0");
        employee.setId(1);
        mapper.updateEmp(employee);
    }

    @Test
    public void testGetEmpsByConditionForeach(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee();
        employee.setGender("0");
        employee.setId(1);
        List<Employee> list = mapper.getEmpsByConditionForeach(Arrays.asList(1,4));
        //list.forEach((o)->System.out.println(o.getLastName()));
    }

    @Test
    public void testAddEmps(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee();
        employee.setGender("0");
        employee.setLastName("添加1");
        Employee employee2 = new Employee();
        employee2.setGender("0");
        employee2.setLastName("添加2");
        List<Employee> emps = new ArrayList<Employee>();
        emps.add(employee);
        emps.add(employee2);
        mapper.addEmps(emps);
    }
}

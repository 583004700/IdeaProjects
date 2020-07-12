package mybatis3.mapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MybatisTest {
    SqlSessionFactory sqlSessionFactory;
    @Before
    public void init() throws Exception{
        String resource = "mybatis3/mapper/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //此时的sqlSessionFactory是DefaultSqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void getEmpReturnMap(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Map<String,Object> map = mapper.getEmpReturnMap(1);
        System.out.println(map.get("id"));
    }

    @Test
    public void getEmpReturnMap2(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Map<String,Employee> map = mapper.getEmpReturnMap2();
        System.out.println(map.get(1));
    }

    @Test
    public void testAdd(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee();
        employee.setEmail(null);
        employee.setGender("男");
        employee.setLastName("小明");
        int row = mapper.addEmp(employee);
        System.out.println(row);
        //添加之后可以获取到员工ID
        System.out.println(employee.getId());
    }

    /**
     * oracle添加
     */
    @Test
    public void testAddO(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Emp emp = new Emp();
        emp.setEname("员工1");
        emp.setDeptno(20);
        emp.setHiredate(new Date());
        emp.setJob("wqsd");
        emp.setMgr(7566);
        emp.setSal(10000.00);
        int row = mapper.addEmpO(emp);
        System.out.println(row);
        //添加之后可以获取到员工ID
        System.out.println(emp.getEmpNo());
    }

    @Test
    public void testList(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        List<Employee> list = mapper.list();
        System.out.println(list.get(0).getLastName());
        System.out.println(list.get(1).getLastName());
        list.get(1).setLastName("小红");
        //System.out.println(list.get(2).getLastName());
        //mapper.updateEmp(list.get(1));

        //mapper.delEmp(list.get(1));
    }

    @Test
    public void testGetEmpById(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = mapper.getEmpById(1);
        System.out.println(employee.getLastName());
    }

    @Test
    public void testGetEmpByIdAndLastName(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = mapper.getEmpByIdAndLastName(1,"tom");
        System.out.println(employee.getLastName());
    }

}

package mybatis6.other;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mybatis.mapper.Department;
import mybatis.mapper.DepartmentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.InputStream;
import java.util.List;

public class MyBatisTest {
    SqlSessionFactory sqlSessionFactory;
    @Before
    public void init() throws Exception{
        String resource = "mybatis6/other/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //此时的sqlSessionFactory是DefaultSqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testGetEmps(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee();
        employee.setId(1);
        PageHelper.startPage(1,5);
        List<Employee> emps = mapper.getEmps(employee);
        PageInfo<Employee> pageInfo = new PageInfo<Employee>(emps);

        System.out.println(emps.size());
    }


    @Test
    public void testBatch(){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for(int i=0;i<10;i++) {
            Employee employee = new Employee();
            employee.setLastName("小明"+i);
            mapper.addEmp(employee);
        }
        sqlSession.commit();
    }

    @Test
    public void testProcedure(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        applicationContext.getBean(DepartmentMapper.class).getDeptListPlus();
    }

}

package mybatis2.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class MybatisTest {
    SqlSessionFactory sqlSessionFactory;
    @Before
    public void init() throws Exception{
        String resource = "mybatis2/config/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //此时的sqlSessionFactory是DefaultSqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    //sqlSession是非线程安全的
    @Test
    public void test() throws Exception{
        //DefaultSqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //第一个参数为mapper的namespace和id
        //SimpleExecutor.query->BaseExecutor.query->SimpleExecutor.doQuery方法
        Employee employee = sqlSession.selectOne("mybatis2.config.EmployeeMapper."+"selectEmp",1);
        System.out.println(employee.getEmail());
        sqlSession.close();
    }

    /**
     * Mapper接口式
     * @throws Exception
     */
    @Test
    public void test2() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //第一个参数为mapper的namespace和id
        Employee employee = sqlSession.getMapper(EmployeeMapper.class).getEmpById(1,null);
        System.out.println(employee.getEmail());
        //开启了驼峰命名才能与实体类对应
        System.out.println(employee.getLastName());
        sqlSession.close();
    }

    /**
     * 注解版
     * @throws Exception
     */
    @Test
    public void test3() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //第一个参数为mapper的namespace和id
        EmployeeAnnotationMapper mapper = sqlSession.getMapper(EmployeeAnnotationMapper.class);
        Employee employee = mapper.testAnnotation(1);
        System.out.println(employee.getEmail());
        sqlSession.close();
    }
}

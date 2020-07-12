package mybatis1.helloWorld;

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
        String resource = "mybatis1/helloWorld/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //此时的sqlSessionFactory是DefaultSqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    //sqlSession是非线程安全的
    //一级缓存保存在executor中
    @Test
    public void test() throws Exception{
        //DefaultSqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //第一个参数为mapper的namespace和id
        //SimpleExecutor.query->BaseExecutor.query->SimpleExecutor.doQuery方法
        Employee employee = sqlSession.selectOne("mybatis1.helloWorld.EmployeeMapper."+"selectEmp",1);
        System.out.println(employee.getEmail());
        sqlSession.close();
    }

    /**
     * 接口式
     * @throws Exception
     */
    @Test
    public void test2() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //第一个参数为mapper的namespace和id
        Employee employee = sqlSession.getMapper(EmployeeMapper.class).getEmpById(1);
        System.out.println(employee.getEmail());
        sqlSession.close();
    }
}

package mybatis5.cache;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class MyBatisTest {
    SqlSessionFactory sqlSessionFactory;
    @Before
    public void init() throws Exception{
        String resource = "mybatis5/cache/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //此时的sqlSessionFactory是DefaultSqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 两级缓存
     * 一级缓存（本地缓存）
     *      与数据库同一次会话期间查询到的数据会放在本地缓存
     *      要保证一级缓存有效
     *          1.sqlSession要同一个对象
     *          2.查询条件相同
     *          3.执行了增删改操作也会使缓存失效
     *          4.不手动清除缓存 sqlSession.clearCache();
     **/
    @Test
    public void testFirstLevelCache(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee();
        employee.setId(1);
        List<Employee> emps = mapper.getEmpsByConditionIf(employee);
        System.out.println(emps.get(0).getLastName());
        List<Employee> emps2 = mapper.getEmpsByConditionIf(employee);
        System.out.println(emps2.get(0).getLastName());
        System.out.println(emps == emps2);
    }

    /*
     *  二级缓存（全局缓存）
     *      一个namespace对应一个二级缓存
     *          使用：
     *      1.开启全局二级缓存配置
     *      2.在mapper中配置使用二级缓存
     *      3.我们的POJO需要实现序列化接口(readOnly为false要用到序列化)
     *  和缓存相关的设置
     *      select标签有个属性useCache=false可以关闭二级缓存
     *      每个增删改标签有个属性flushCache默认为true，所以会清空一级缓存和二级缓存
     *      sqlSession.clearCache只会清空一级缓存
     *      localCacheScope：本地缓存作用域，
     *          SESSION：本次会话有效
     *          STATEMENT：相当于禁用掉本地缓存
     */
    @Test
    public void testSecondLevelCache(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        EmployeeMapper mapper2 = sqlSession2.getMapper(EmployeeMapper.class);
        System.out.println("mapper == mapper2"+(mapper == mapper2));
        Employee employee = new Employee();
        employee.setId(1);
        List<Employee> emps = mapper.getEmpsByConditionIf(employee);
        System.out.println(emps.get(0).getLastName());
        //sqlSession关闭时会将查询到的数据放到缓存中
        sqlSession.close();
        List<Employee> emps2 = mapper2.getEmpsByConditionIf(employee);
        System.out.println(emps2.get(0).getLastName());
        System.out.println(emps == emps2);
        sqlSession2.close();

    }

}

package common.test;

import common.mapper.EntityProvider;
import org.junit.Before;
import org.junit.Test;

public class PersonTest {

    @Before
    public void init() throws Exception{

    }

    @Test
    public void testInsert() throws Exception{
        //会插入person非空字段
        Person person = new Person();
        person.setName("张三");
        String sql = new EntityProvider().insert(person);
        System.out.println(sql);
    }

    @Test
    public void testUpdate() throws Exception{
        //会更新指定条件指定字段，更新id为5的姓名为张三
        Person person = new Person();
        person.setName("张三");
        person.setId(5);
        String sql = new EntityProvider().update(person,"name","id");
        System.out.println(sql);
    }

    @Test
    public void testUpdateByPrimary() throws Exception{
        //会更新指定条件指定字段，更新id为5的姓名为张三
        Person person = new Person();
        person.setName("张三");
        person.setId(5);
        String sql = new EntityProvider().updateByPrimaryKey(person);
        System.out.println(sql);
    }
}

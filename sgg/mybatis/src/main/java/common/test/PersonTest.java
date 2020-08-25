package common.test;

import common.mapper.EntityProvider;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PersonTest {
    @Autowired
    PersonMapper personMapper;

    @Test
    public void testInsert() throws Exception{
        //会插入person非空字段
        Person person = new Person();
        person.setName("张三");
        personMapper.insert(person);
    }

    @Test
    public void testUpdate() throws Exception{
        Person person = new Person();
        person.setName("张三").setId(5).setEmail("222@qq.com");
        //相当于 update person set email = '222@qq.com' where id = 5 and name = '张三'
        int row = personMapper.update(person,"email","id,name");
    }

    @Test
    public void testQuery() throws Exception{
        Person person = new Person();
        person.setName("张三").setId(5).setEmail("222@qq.com");
        //相当于 select t.* from person t where id = 5 and name = '张三'
        List<Person> personList = personMapper.selectList(person,"id,name");
        //相当于 select t.* from person t where t.id = 5
        Person newPerson = personMapper.selectOne(person,"id");
    }
}

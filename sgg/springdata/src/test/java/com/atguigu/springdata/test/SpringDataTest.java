package com.atguigu.springdata.test;

import com.atguigu.springdata.Person;
import com.atguigu.springdata.PersonRepository;
import com.atguigu.springdata.PersonService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SpringDataTest {
    private ApplicationContext ctx;
    private PersonRepository personRepository = null;
    private PersonService personService;

    {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        personRepository = ctx.getBean(PersonRepository.class);
        personService = ctx.getBean(PersonService.class);
    }

    @Test
    public void testCustomRepository(){
        personRepository.test();
    }

    /**
     * 实现带查询条件的分页
     */
    @Test
    public void testJpaSpecificationExecutor(){
        int pageNo = 3 - 1;
        int pageSize = 5;
        PageRequest pageable = new PageRequest(pageNo,pageSize);
        Specification<Person> specification = new Specification<Person>() {
            /**
             *
             * @param root 代表查询的实体类
             * @param criteriaQuery 可以从中得到root对象，即告知JPA Criteria 查询要查询哪一个实体类，还可以
             *                      来添加查询条件，还可以结合EntityManager 对象得到最终查询到的TypeQuery对象
             * @param criteriaBuilder
             * @return
             */
            public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path path = root.get("id");
                Predicate predicate = criteriaBuilder.ge(path,5);
                return predicate;
            }
        };
        Page<Person> page = personRepository.findAll(specification,pageable);
    }

    @Test
    public void testJpaRepository(){
        Person person = new Person();
        person.setBirth(new Date());
        person.setEmail("aaa@qq.com");
        person.setLastName("aab");
        person.setId(2);
        personRepository.saveAndFlush(person);
    }

    @Test
    public void testPagingAndSortingRepository(){
        //pageNo从0开始
        int pageNo = 3;
        int pageSize = 5;
        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC,"id");
        Sort.Order order2 = new Sort.Order(Sort.Direction.ASC,"email");
        Sort sort = new Sort(order1,order2);
        PageRequest pageable = new PageRequest(pageNo,pageSize,sort);
        Page<Person> page = personRepository.findAll(pageable);
        System.out.println(page.getTotalElements());
        System.out.println(page.getContent());
    }

    @Test
    public void testCrudRepository(){
        List<Person> persons = new ArrayList<Person>();
        for(int i='a';i<'z';i++){
            Person person = new Person();
            person.setAddressId(i+1);
            person.setBirth(new Date());
            person.setEmail((char)i+"@qq.com");
            person.setLastName("i"+i);
            persons.add(person);
        }
        personService.savePersons(persons);
    }

    @Test
    public void testModifying(){
        personService.updatePersonEmail("hehe@qq.com",1);
    }

    @Test
    public void getNativeQuery(){
        long count = personRepository.getTotalCount();
        System.out.println(count);
    }

    @Test
    public void testQueryAnnotationLikeParam(){
//        List<Person> persons = personRepository.testQueryAnnotationLikeParam("%A%","%bb%");
//        System.out.println(persons.size());

        List<Person> persons = personRepository.testQueryAnnotationLikeParam("A","bb");
        System.out.println(persons.size());
    }

    @Test
    public void testQueryAnnotationParams2(){
        List<Person> persons = personRepository.testQueryAnnotationParams2("aa@qq.com","AA");
        System.out.println(persons);
    }

    @Test
    public void testQueryAnnotationParams1(){
        List<Person> persons = personRepository.testQueryAnnotationParams1("AA","aa@qq.com");
        System.out.println(persons);
    }

    @Test
    public void testQueryAnnotation(){
        Person person = personRepository.getMaxIdPerson();
        System.out.println(person);
    }

    @Test
    public void testKeyWords2(){
        List<Person> persons = personRepository.getByAddress_IdGreaterThan(1);
        System.out.println(persons.size());
    }

    @Test
    public void testKeyWords(){
        List<Person> persons = personRepository.getByLastNameStartingWithAndIdLessThan("B",10);
        System.out.println(persons.size());

        persons = personRepository.getByLastNameEndingWithAndIdLessThan("B",10);
        System.out.println(persons.size());

        persons = personRepository.getByEmailInOrBirthLessThan(Arrays.asList("AA","BB"),new Date());
        System.out.println(persons.size());
    }

    @Test
    public void testHelloWorldSpringData(){
        System.out.println(personRepository.getClass().getName());
        Person person = personRepository.getByLastName("AA");
        System.out.println(person);
    }

    @Test
    public void testDataSource(){
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource);
    }

    @Test
    public void testJpa(){

    }
}

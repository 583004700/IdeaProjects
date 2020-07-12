package com.atguigu.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * 1. Repository 是一个空接口. 即是一个标记接口
 * 2. 若我们定义的接口继承了 Repository, 则该接口会被 IOC 容器识别为一个 Repository Bean.
 * 纳入到 IOC 容器中. 进而可以在该接口中定义满足一定规范的方法.
 *
 * 3. 实际上, 也可以通过 @RepositoryDefinition 注解来替代继承 Repository 接口
 */
/**
 * 在 Repository 子接口中声明方法
 * 1. 不是随便声明的. 而需要符合一定的规范
 * 2. 查询方法以 find | read | get 开头
 * 3. 涉及条件查询时，条件的属性用条件关键字连接
 * 4. 要注意的是：条件属性以首字母大写。
 * 5. 支持属性的级联查询. 若当前类有符合条件的属性, 则优先使用, 而不使用级联属性.
 * 若需要使用级联属性, 则属性之间使用 _ 进行连接.
 */
//@RepositoryDefinition(domainClass = Person.class,idClass = Integer.class)
public interface PersonRepository extends JpaRepository<Person,Integer>,
        JpaSpecificationExecutor<Person>,PersonDao {
    Person getByLastName(String lastName);

    //where lastName like ?% and id < ?
    List<Person> getByLastNameStartingWithAndIdLessThan(String lastName,Integer id);

    //where lastName like %? and id < ?
    List<Person> getByLastNameEndingWithAndIdLessThan(String lastName,Integer id);

    //where email in (?,?,?) or birth < ?
    List<Person> getByEmailInOrBirthLessThan(List<String> emails, Date birth);

    //where a.id > ?
    List<Person> getByAddress_IdGreaterThan(Integer id);

    //查询ID值最大的那个person
    @Query("select p from Person p where p.id = (select max(p2.id) from Person p2)")
    Person getMaxIdPerson();

    //为Query注解传递参数的方式1：使用占位符
    @Query("select p from Person p where p.lastName = ?1 and p.email = ?2")
    List<Person> testQueryAnnotationParams1(String lastName,String email);

    //为Query注解传递参数的方式1：使用命名参数方式
    @Query("select p from Person p where p.lastName = :lastName and p.email = :email")
    List<Person> testQueryAnnotationParams2(@Param("email") String email,@Param("lastName") String lastName);

    //SpringData可以在参数处添加%
    @Query("select p from Person p where p.lastName like %?1% or p.email like %?2%")
    List<Person> testQueryAnnotationLikeParam(String lastName,String email);

    @Query(value = "select count(id) from jpa_persons",nativeQuery = true)
    long getTotalCount();

    @Modifying
    @Query("update Person p set p.email = :email where p.id = :id")
    void updatePersonEmail(@Param("id") Integer id,@Param("email") String email);
}

package com.atguigu.jpa.test;

import com.atguigu.jpa.helloworld.Category;
import com.atguigu.jpa.helloworld.Customer;
import com.atguigu.jpa.helloworld.Department;
import com.atguigu.jpa.helloworld.Item;
import com.atguigu.jpa.helloworld.Manager;
import com.atguigu.jpa.helloworld.Order;
import org.hibernate.ejb.QueryHints;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class JPATest {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @Before
    public void init(){
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @After
    public void destroy(){
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    //可以使用 JPQL 完成 UPDATE 和 DELETE 操作.
    @Test
    public void testExecuteUpdate(){
        String jpql = "UPDATE Customer c SET c.lastName = ? WHERE c.id = ?";
        Query query = entityManager.createQuery(jpql).setParameter(1, "YYY").setParameter(2, 12);

        query.executeUpdate();
    }

    //使用 jpql 内建的函数
    @Test
    public void testJpqlFunction(){
        String jpql = "SELECT lower(c.email) FROM Customer c";

        List<String> emails = entityManager.createQuery(jpql).getResultList();
        System.out.println(emails);
    }

    @Test
    public void testSubQuery(){
        //查询所有 Customer 的 lastName 为 YY 的 Order
        String jpql = "SELECT o FROM Order o "
                + "WHERE o.customer = (SELECT c FROM Customer c WHERE c.lastName = ?)";

        Query query = entityManager.createQuery(jpql).setParameter(1, "YY");
        List<Order> orders = query.getResultList();
        System.out.println(orders.size());
    }

    /**
     * JPQL 的关联查询同 HQL 的关联查询.
     */
    @Test
    public void testLeftOuterJoinFetch(){
        String jpql = "FROM Customer c LEFT OUTER JOIN FETCH c.orders WHERE c.id = ?";

        Customer customer =
                (Customer) entityManager.createQuery(jpql).setParameter(1, 12).getSingleResult();
        System.out.println(customer.getLastName());
        System.out.println(customer.getOrders().size());

//		List<Object[]> result = entityManager.createQuery(jpql).setParameter(1, 12).getResultList();
//		System.out.println(result);
    }

    //查询 order 数量大于 2 的那些 Customer
    @Test
    public void testGroupBy(){
        String jpql = "SELECT o.customer FROM Order o "
                + "GROUP BY o.customer "
                + "HAVING count(o.id) >= 2";
        List<Customer> customers = entityManager.createQuery(jpql).getResultList();

        System.out.println(customers);
    }

    @Test
    public void testOrderBy(){
        String jpql = "from Customer c where c.age > ? order by c.age desc";
        Query query = entityManager.createQuery(jpql).setHint(QueryHints.HINT_CACHEABLE,true).setParameter(1,1);
        List<Customer> customers = query.getResultList();
        System.out.println(customers.size());
    }

    @Test
    public void testQueryCache(){
        String jpql = "from Customer c where c.age > ?";
        Query query = entityManager.createQuery(jpql).setHint(QueryHints.HINT_CACHEABLE,true).setParameter(1,1);
        List<Customer> customers = query.getResultList();
        System.out.println(customers);

        List<Customer> customers2 = query.getResultList();
        System.out.println(customers2);
    }

    //本地sql
    @Test
    public void testNativeQuery(){
        String sql = "select age from jpa_customers where id = ?";
        Query query = entityManager.createNativeQuery(sql).setParameter(1,2);
        Object result = query.getSingleResult();
        System.out.println(result);
    }

    @Test
    public void testNamedQuery(){
        Query query = entityManager.createNamedQuery("testNamedQuery").setParameter(1,2);
        Customer customer = (Customer) query.getSingleResult();
        System.out.println(customer);
    }

    //默认情况下，若只查询部分属性，则将返回Object[] 类型的结果，或者Object[] 类型的List。
    //也可以在实体类中创建对应的构造器，然后在JPQL语句中利用对应的构造器返回实体类的对象。
    @Test
    public void testPartlyProperties(){
        String jpql = "select new Customer (c.lastName, c.age) from Customer c where c.id > ?";
        List customers = entityManager.createQuery(jpql).setParameter(1,1).getResultList();
        System.out.println(customers);
    }

    @Test
    public void testHelloJPQL(){
        String jpql = "FROM Customer c where c.age > ?";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(1,1);
        List<Customer> customers = query.getResultList();
        System.out.println("customers---------------"+customers);
    }


    @Test
    public void testSecondLevelCache(){
        Customer customer1 = entityManager.find(Customer.class,2);
        transaction.commit();
        entityManager.close();

        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer2 = entityManager.find(Customer.class,2);
    }

    @Test
    public void testManyToManyFind(){
        Item item = entityManager.find(Item.class,1);
        System.out.println(item.getItemName());
        System.out.println(item.getCategories().size());
    }

    @Test
    public void testManyToManyPersistence(){
        Item i1 = new Item();
        i1.setItemName("i-1");

        Item i2 = new Item();
        i2.setItemName("i-2");

        Category c1 = new Category();
        c1.setCategoryName("c-1");

        Category c2 = new Category();
        c2.setCategoryName("c-2");

        i1.getCategories().add(c1);
        i1.getCategories().add(c2);

        i2.getCategories().add(c1);
        i2.getCategories().add(c2);
        entityManager.persist(i1);
        entityManager.persist(i2);
        entityManager.persist(c1);
        entityManager.persist(c2);
    }

    @Test
    public void testOneToOneFind2(){
        Manager mgr = entityManager.find(Manager.class,1);
        System.out.println(mgr.getMgrName());
        System.out.println(mgr.getDepartment().getClass().getName());
    }

    @Test
    public void testOneToOneFind(){
        Department dept = entityManager.find(Department.class,3);
        System.out.println(dept.getDeptName());
        System.out.println(dept.getMgr().getClass().getName());
    }

    @Test
    public void testOneToOnePersistence(){
        Manager manager = new Manager();
        manager.setMgrName("M-AA");

        Department dept = new Department();
        dept.setDeptName("D-AA");
        dept.setMgr(manager);
        entityManager.persist(manager);
        entityManager.persist(dept);
    }

    @Test
    public void testUpdate(){
        Customer customer = entityManager.find(Customer.class,7);
        customer.getOrders().iterator().next().setOrderName("O-XXX-7");
    }

    //默认情况下，若删除1的一端，则会先把关联的n的一端的外键置空，然后再删除，可以通过OneToMany注解的cascade属性修改删除策略
    @Test
    public void testOneToManyRemove(){
        Customer customer = entityManager.find(Customer.class,6);
        entityManager.remove(customer);
    }

    //默认对多的一方使用懒加载
    @Test
    public void testOneToManyFind(){
        Customer customer = entityManager.find(Customer.class,6);
        System.out.println(customer.getLastName());
        System.out.println(customer.getOrders().size());
    }

    @Test
    public void testOneToManyPersist(){
        Customer customer = new Customer();
        customer.setAge(20);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("zz@163.com");
        customer.setLastName("ZZ");

        Order order1 = new Order();
        order1.setOrderName("O-ZZ-1");

        Order order2 = new Order();
        order2.setOrderName("O-ZZ-2");
//        customer.getOrders().add(order1);
//        customer.getOrders().add(order2);
        order1.setCustomer(customer);
        order2.setCustomer(customer);

        entityManager.persist(customer);
        entityManager.persist(order1);
        entityManager.persist(order2);
    }

    @Test
    public void testManyToOneUpdate(){
        Order order = entityManager.find(Order.class,2);
//        order.getCustomer().setLastName("MMM");
    }

    @Test
    public void testManyToOneRemove(){
//        Order order = entityManager.find(Order.class,1);
//        entityManager.remove(order);

        Customer customer = entityManager.find(Customer.class,3);
        entityManager.remove(customer);
    }

    //默认情况下，使用左外连接的方式来获取n的一端的对象和其关联的1的一端的对象
    @Test
    public void testManyToOneFind(){
        Order order = entityManager.find(Order.class,1);
        System.out.println(order.getOrderName());
//        System.out.println(order.getCustomer().getLastName());
    }

    /**
     * 保存多对一时，建议会保存一的一端，再保存多的一端，不会有额外的update语句
     */
    @Test
    public void testManyToOnePersist(){
        Customer customer = new Customer();
        customer.setAge(20);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("gg@163.com");
        customer.setLastName("GG");

        Order order1 = new Order();
        order1.setOrderName("O-FF-1");

        Order order2 = new Order();
        order2.setOrderName("O-FF-2");

//        order1.setCustomer(customer);
//        order2.setCustomer(customer);
        entityManager.persist(customer);
        entityManager.persist(order1);
        entityManager.persist(order2);
    }

    @Test
    public void testRefresh(){
        Customer customer = entityManager.find(Customer.class,1);
        customer = entityManager.find(Customer.class,1);//如果不refresh，则从缓存中取出数据
        entityManager.refresh(customer);//重新发送sql语句查询
    }

    @Test
    public void testFlush(){
        Customer customer = entityManager.find(Customer.class,1);
        System.out.println(customer);

        customer.setLastName("BB");//如果不flush，提交事务时才发送update语句

        entityManager.flush();//发送update,但没提交事务,其它事务还看不到
    }

    //若传入的是一个游离对象，即传入的对象有OID。
    //1.若在EntityManager 缓存中有该对象
    @Test
    public void testMerge4(){
        Customer customer = new Customer();
        customer.setAge(20);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("ff@163.com");
        customer.setLastName("FF");
        customer.setId(2);

        Customer customer2 = entityManager.find(Customer.class,2);
        entityManager.merge(customer);
        System.out.println(customer);
        System.out.println(customer2);
    }

    //若传入的是一个游离对象，即传入的对象有OID。
    //1.若在EntityManager 缓存中没有该对象
    //2.若在数据库中有对象记录
    //3.JPA会查询对应的记录，然后返回该记录对应的对象，再然后把游离对象的属性复制到查询到的对象中
    //4.对查询到的对象执行update
    @Test
    public void testMerge3(){
        Customer customer = new Customer();
        customer.setAge(20);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("ee@163.com");
        customer.setLastName("EE");
        customer.setId(1);

        Customer customer2 = entityManager.merge(customer);
        System.out.println(customer);
        System.out.println(customer2);
    }

    //若传入的是一个游离对象，即传入的对象有OID。
    //1.若在EntityManager 缓存中没有该对象
    //2.若在数据库中也没有对象记录
    //3.JPA会创建一个新的对象，然后把当前游离的对象的属性复制到新创建的对象中
    //4.对新对象执行insert操作
    @Test
    public void testMerge2(){
        Customer customer = new Customer();
        customer.setAge(20);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("dd@163.com");
        customer.setLastName("DD");
        customer.setId(100);

        Customer customer2 = entityManager.merge(customer);
        System.out.println(customer.getId());
        System.out.println(customer2.getId());
    }

    @Test
    //若传入的是一个临时对象
    public void testMerge1(){
        Customer customer = new Customer();
        customer.setAge(18);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("cc@163.com");
        customer.setLastName("CC");

        Customer customer2 = entityManager.merge(customer);
        System.out.println(customer.getId());
        System.out.println(customer2.getId());
    }

    @Test
    public void testRemove(){
//        Customer customer = new Customer();
//        customer.setId(100);
        Customer customer = entityManager.find(Customer.class,100);
        entityManager.remove(customer);
    }

    @Test
    public void testPersistence(){
        Customer customer = new Customer();
        customer.setAge(15);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("bb@163.com");
        entityManager.persist(customer);
        System.out.println(customer.getId());
    }

    @Test
    public void testGetReference(){
        Customer customer = entityManager.getReference(Customer.class,100);
        System.out.println("---------------------------------------");
//        transaction.commit();
//        entityManager.close();
        System.out.println(customer);
    }

    @Test
    public void testFind(){
        Customer customer = entityManager.find(Customer.class,100);
        System.out.println("---------------------------------------");
        System.out.println(customer);
    }

}

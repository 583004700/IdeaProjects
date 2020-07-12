package com.atguigu.jpa.helloworld;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NamedQuery(name="testNamedQuery", query="from Customer c where c.id = ?")
@Cacheable(true)
@Setter
@Getter
@Entity
@Table(name = "JPA_CUSTOMERS")
public class Customer {

//    @TableGenerator(name="ID_GENERATOR",table = "jpa_id_generators",pkColumnName = "PK_NAME",
//            pkColumnValue = "CUSTOMER_ID",valueColumnName = "PK_VALUE",allocationSize = 100)
//    @GeneratedValue(strategy = GenerationType.TABLE,generator = "ID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    @Column(name = "LAST_NAME")
    private String lastName;

    private String email;
    private int age;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.DATE)
    private Date birth;

    //映射一对多
//    @JoinColumn(name="CUSTOMER_ID")
    //mappedBy由多的一方的customer属性维护关联关系，一的一方放弃，不要再定义@JoinColumn注解了
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.REMOVE},mappedBy = "customer")
    private Set<Order> orders = new HashSet<Order>();

    public String getInfo() {
        return "Customer{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }

    public Customer() {
    }

    public Customer(String lastName, int age) {
        this.lastName = lastName;
        this.age = age;
    }
}

package com.atguigu.jpa.helloworld;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="JPA_ORDERS")
@Setter
@Getter
public class Order {

    @GeneratedValue
    @Id
    private Integer id;
    @Column(name = "ORDER_NAME")
    private String orderName;
    //映射单向 n-1的关联关系,fetch属性来修改默认的关联属性的加载策略
    @JoinColumn(name = "CUSTOMER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;
}

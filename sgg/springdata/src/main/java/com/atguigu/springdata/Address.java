package com.atguigu.springdata;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JPA_ADDRESSES")
@Setter
@Getter
public class Address {
    @Id
    @GeneratedValue
    private Integer id;
    private String province;
    private String city;
}

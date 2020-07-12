package com.atguigu.springdata;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name="JPA_PERSONS")
@Entity
@Setter
@Getter
public class Person {
    @Id
    @GeneratedValue
    private Integer id;
    private String lastName;
    private String email;
    private Date birth;

    @Column(name = "ADD_ID")
    private Integer addressId;

    @JoinColumn(name = "ADDRESS_ID")
    @ManyToOne
    private Address address;
}

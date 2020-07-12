package com.atguigu.jpa.helloworld;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "JPA_MANAGERS")
@Setter
@Getter
public class Manager {

    @GeneratedValue
    @Id
    private Integer id;
    @Column(name = "MGR_NAME")
    private String mgrName;

    @OneToOne(mappedBy = "mgr")
    private Department department;
}

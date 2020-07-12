package com.atguigu.jpa.helloworld;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "JPA_CATEGORYS")
public class Category {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    private Set<Item> items = new HashSet<Item>();
}

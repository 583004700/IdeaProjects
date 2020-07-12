package com.atguigu.jpa.helloworld;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "JPA_ITEMS")
public class Item {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name="ITEM_NAME")
    private String itemName;

    @JoinTable(name = "ITEM_CATEGORY",joinColumns = {@JoinColumn(name = "ITEM_ID",referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID",referencedColumnName = "ID")})
    @ManyToMany
    private Set<Category> categories = new HashSet<Category>();
}

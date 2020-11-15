package com.demo.mydemo.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
//@TableName(value = "tbl_employee")
public class Employee {
    //列名与属性名相同时，value可以不设置，type指定主键策略
//    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "last_name")
    private String lastName;
    private String email;
    private Integer gender;
    private Integer age;
    //exist = false ，忽略当前字段
    @TableField(exist = false)
    private Double salary;

    @Override
    public String toString() {
        return "Employee [id=" + id + ", lastName=" + lastName + ", email=" + email
                + ", gender=" + gender + ", age="
                + age + "]";
    }
}

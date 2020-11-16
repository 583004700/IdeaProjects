package com.demo.mydemo.mp2.beans;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Setter
@Getter
@Accessors(chain = true)
public class Employee extends Model<Employee> {
    private Integer id;
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

    /**
     * 指定当前实体类的主键
     * @return
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}

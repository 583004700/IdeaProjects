package com.demo.mydemo.mp5.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.Version;
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

    //乐观锁插件必须使用@Version注解
    @Version
    private Integer version;

    @Override
    public String toString() {
        return "Employee [id=" + id + ", lastName=" + lastName + ", email=" + email
                + ", gender=" + gender + ", age="
                + age + "]";
    }
}

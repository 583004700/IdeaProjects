package com.atguigu.securitydemo1.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Users {
    private Integer id;
    private String username;
    private String password;
}

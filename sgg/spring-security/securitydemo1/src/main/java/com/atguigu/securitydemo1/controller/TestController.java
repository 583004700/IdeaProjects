package com.atguigu.securitydemo1.controller;

import com.atguigu.securitydemo1.entity.Users;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("hello")
    public String hello(){
        return "hello security";
    }

    @GetMapping("index")
    public String index(){
        return "hello index";
    }

    @GetMapping("update")
    // 表示用户需要 sale 或者 manager角色才能访问这个方法，需要开启@EnableGlobalMethodSecurity(securedEnabled = true)
    //@Secured({"ROLE_sale","ROLE_manager"})
    // 表示用户需要 权限或者角色才能访问这个方法，方法之前校验，需要开启@EnableGlobalMethodSecurity(prePostEnabled = true)
    //@PreAuthorize("hasAnyAuthority('admins')")
    // 表示用户需要 权限或者角色才能访问这个方法，方法之后校验,方法会执行，但会跳转到没有权限页面,需要开启@EnableGlobalMethodSecurity(prePostEnabled = true)
    @PostAuthorize("hasAnyAuthority('admins')")
    public String update(){
        System.out.println("update......");
        return "hello update";
    }

    @GetMapping("getAll")
    @PostAuthorize("hasAnyAuthority('admins')")
    @PostFilter("filterObject.username=='zs'")    // 对返回值进行过滤，只返回username为zs的
    public List<Users> getAllUser(){
        ArrayList<Users> list = new ArrayList<Users>();
        list.add(new Users().setUsername("zs"));
        list.add(new Users().setUsername("ls"));
        System.out.println(list);
        return list;
    }
}

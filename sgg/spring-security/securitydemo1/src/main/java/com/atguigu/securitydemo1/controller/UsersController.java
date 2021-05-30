package com.atguigu.securitydemo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class UsersController {
    @RequestMapping("login")
    public String login(){
        return "login";
    }
}

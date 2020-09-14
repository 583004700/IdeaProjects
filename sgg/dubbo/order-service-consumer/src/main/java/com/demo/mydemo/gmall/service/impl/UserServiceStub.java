package com.demo.mydemo.gmall.service.impl;

import com.demo.mydemo.gmall.bean.UserAddress;
import com.demo.mydemo.gmall.service.UserService;
import org.springframework.util.StringUtils;

import java.util.List;

public class UserServiceStub implements UserService {

    private final UserService userService;

    /**
     * 传入的是 UserService远程代理对象
     * @param userService
     */
    public UserServiceStub(UserService userService){
        this.userService = userService;
    }

    @Override
    public List<UserAddress> getUserAddressList(String userId) {
        System.out.println("UserServiceStub:"+userId);
        if(!StringUtils.isEmpty(userId)){
            return userService.getUserAddressList(userId);
        }
        return null;
    }
}

package com.demo.mydemo.gmall.service.impl;

import com.demo.mydemo.gmall.bean.UserAddress;
import com.demo.mydemo.gmall.service.UserService;

import java.util.Arrays;
import java.util.List;

public class UserServiceImpl implements UserService {
    public List<UserAddress> getUserAddressList(String userId) {
        System.out.println("--------------------");
        UserAddress address1 = new UserAddress(1,"北京","1","李四","135","Y");
        UserAddress address2 = new UserAddress(2,"上海","1","王五","137","");
        return Arrays.asList(address1,address2);
    }
}

package com.demo.mydemo.gmall.service.impl;

import com.demo.mydemo.gmall.bean.UserAddress;
import com.demo.mydemo.gmall.service.OrderService;
import com.demo.mydemo.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    UserService userService;

    public List<UserAddress> initOrder(String userId) {
        List<UserAddress> addressList = userService.getUserAddressList(userId);
        System.out.println(addressList);
        return addressList;
    }
}

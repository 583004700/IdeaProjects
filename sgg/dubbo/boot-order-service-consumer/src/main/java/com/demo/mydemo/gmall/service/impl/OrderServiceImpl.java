package com.demo.mydemo.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.mydemo.gmall.bean.UserAddress;
import com.demo.mydemo.gmall.service.OrderService;
import com.demo.mydemo.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    //@Autowired
    //可以直接配置url，不用连接注册中心也可以
    @Reference(url = "hadoop102:20880")
    UserService userService;

    public List<UserAddress> initOrder(String userId) {
        List<UserAddress> addressList = userService.getUserAddressList(userId);
        return addressList;
    }
}

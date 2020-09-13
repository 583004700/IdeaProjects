package com.demo.mydemo.gmall.service;

import com.demo.mydemo.gmall.bean.UserAddress;

import java.util.List;

public interface UserService {
    List<UserAddress> getUserAddressList(String userId);
}

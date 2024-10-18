package com.example.uaa.service.mp;

import com.example.uaa.model.vo.UserVo;
import com.example.uaa.service.rpc.UserService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class UserServiceImpl implements UserService {
    @Override
    public UserVo getUser() {
        return new UserVo().setName("张三");
    }
}

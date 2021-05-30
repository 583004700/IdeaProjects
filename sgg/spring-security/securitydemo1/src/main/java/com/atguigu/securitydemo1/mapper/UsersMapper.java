package com.atguigu.securitydemo1.mapper;

import com.atguigu.securitydemo1.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/*
    @Repository也可以不加，但是idea会报红
 */
@Repository
public interface UsersMapper extends BaseMapper<Users> {
}

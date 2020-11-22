package com.demo.mydemo.mp5.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.demo.mydemo.mp5.beans.Employee;

public interface EmployeeMapper extends BaseMapper<Employee> {
    /**
     * 自定义mapper的注入
     * @return
     */
    int deleteAll();
}

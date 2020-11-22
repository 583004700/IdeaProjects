package com.demo.mydemo.mp3.service.impl;

import com.demo.mydemo.mp3.beans.Employee;
import com.demo.mydemo.mp3.mapper.EmployeeMapper;
import com.demo.mydemo.mp3.service.EmployeeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhuwb
 * @since 2020-11-21
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}

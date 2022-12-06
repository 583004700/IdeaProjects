package com.demo.mydemo.service.impl;

import com.demo.mydemo.entity.Dept;
import com.demo.mydemo.service.DeptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    public List<Dept> getAllDepts(){
        List<Dept> allDepts = new ArrayList<Dept>();
        Dept dept1 = new Dept();
        dept1.setDeptCode("001");
        dept1.setDeptName("技术部");
        Dept dept2 = new Dept();
        dept2.setDeptCode("002");
        dept2.setDeptName("人事部");
        allDepts.add(dept1);
        allDepts.add(dept2);
        return allDepts;
    }
}

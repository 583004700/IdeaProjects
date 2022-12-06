package com.demo.mydemo.controller;

import com.demo.mydemo.entity.Dept;
import com.demo.mydemo.service.DeptService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dept")
@Api(tags = {"部门模块"})
public class DeptController {
    @Autowired
    private DeptService deptService;
    @RequestMapping("/list")
    public List<Dept> list(){
        return deptService.getAllDepts();
    }
}

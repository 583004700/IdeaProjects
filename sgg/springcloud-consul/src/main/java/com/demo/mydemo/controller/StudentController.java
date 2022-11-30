package com.demo.mydemo.controller;

import com.demo.mydemo.entity.Student;
import com.demo.mydemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private Environment environment;

    @RequestMapping("/list")
    public List<Student> list() {
        String port = environment.getProperty("server.port");
        System.out.println("port：" + port);
        return studentService.getAllStudent();
    }
}

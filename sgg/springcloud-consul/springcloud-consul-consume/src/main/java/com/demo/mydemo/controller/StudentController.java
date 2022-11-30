package com.demo.mydemo.controller;

import com.demo.mydemo.entity.Student;
import com.demo.mydemo.feign.StudentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/consume/student")
public class StudentController {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    StudentFeign studentFeign;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestMapping("/list")
    public List<Student> list() {
        //return restTemplate.getForObject("http://consul-application/student/list", List.class);
        return studentFeign.getAllStudent();
    }
}

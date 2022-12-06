package com.demo.mydemo.feign;

import com.demo.mydemo.entity.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "consul-student-provider")
public interface StudentFeign {

    @RequestMapping("/student/list")
    List<Student> getAllStudent();

}

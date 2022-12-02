package com.demo.mydemo.service.impl;

import com.demo.mydemo.entity.Student;
import com.demo.mydemo.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Override
    public List<Student> getAllStudent() {
        List<Student> allStudents = new ArrayList<Student>();
        Student s1 = new Student().setId("1").setName("张三");
        Student s2 = new Student().setId("1").setName("李四");
        allStudents.add(s1);
        allStudents.add(s2);
        return allStudents;
    }
}

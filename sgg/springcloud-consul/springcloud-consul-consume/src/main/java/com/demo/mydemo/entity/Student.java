package com.demo.mydemo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class Student {
    private String id;
    private String name;
}

package com.atguigu.mq.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class Book {
    private String bookName;
    private String author;
}

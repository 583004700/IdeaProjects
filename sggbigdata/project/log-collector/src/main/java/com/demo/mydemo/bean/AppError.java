package com.demo.mydemo.bean;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppError {
    private String errorBrief;    //错误摘要
    private String errorDetail;   //错误详情
}

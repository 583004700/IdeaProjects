package com.demo.mydemo.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 错误日志
 */
@Setter
@Getter
public class AppErrorLog {
    private String errorBrief;    //错误摘要
    private String errorDetail;   //错误详情
}

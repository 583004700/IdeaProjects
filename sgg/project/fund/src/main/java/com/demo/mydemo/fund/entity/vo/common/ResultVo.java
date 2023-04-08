package com.demo.mydemo.fund.entity.vo.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResultVo<T> {
    private int code = 0;
    private T result;
    private String message = "ok";
    private String type = "success";
}

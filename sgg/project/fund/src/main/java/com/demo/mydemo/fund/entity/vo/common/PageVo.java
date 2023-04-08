package com.demo.mydemo.fund.entity.vo.common;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class PageVo<T> {
    private List<T> items = new ArrayList<>();
    private int total;
}

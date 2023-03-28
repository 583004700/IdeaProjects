package com.demo.mydemo.fund.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class Fund {
    private String fundcode;
    private String name;
    private String type;
    /**
     * 估算涨跌幅
     */
    private BigDecimal gszzl;
    /**
     * 时间
     */
    private String gztime;
}

package com.demo.mydemo.fund.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@TableName("fund_gs")
public class FundGsPo {
    private Long id;
    private String fundcode;
    private String name;
    private String type;
    private BigDecimal gszzl;
    private String gzdate;
    private Date gztime;
    private Date updatedTime;
}

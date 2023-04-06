package com.demo.mydemo.fund.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class FundVo {
    private Long id;
    private String fundcode;
    private String name;
    private String type;
    private BigDecimal gszzl;
    private String gzdate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date gztime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
}

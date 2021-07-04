package com.itheima.demo.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 出差申请中的流程变量对象
 */
@Setter
@Getter
public class Evection implements Serializable {
    /**
     * 主键Id
     */
    private Long id;

    /**
     * 出差单的名字
     */
    private String evectionName;

    /**
     * 出差天数
     */
    private Double num;

    /**
     * 出差开始时间
     */
    private Date beginDate;

    /**
     * 出差结束时间
     */
    private Date endDate;

    /**
     * 目的地
     */
    private String destination;

}

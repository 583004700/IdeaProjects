package com.demo.mydemo.fund.service;

import com.demo.mydemo.fund.entity.Fund;
import com.demo.mydemo.fund.entity.po.FundGsPo;
import com.demo.mydemo.fund.entity.vo.FundVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FundService {
    /**
     * 通过接口查询所有基金
     *
     * @return
     */
    Map<String, Fund> getAllFund();

    /**
     * 通过接口通过基金代码查询基金实时涨跌的估算值
     *
     * @param fund
     * @return
     */
    Fund getFundByCode(Fund fund);

    /**
     * 通过接口查询每个基金的实时数据，并按从大到小排序
     *
     * @return
     */
    List<Fund> getGszSort();

    /**
     * 批量插入查询结果到数据库
     *
     * @return
     */
    int insertBatch();

    int insertBatchHistory(Date date);

    /**
     * 通过参数查询基金列表
     *
     * @param param
     * @return
     */
    List<FundGsPo> selectList(@Param("param") Map<String, Object> param);

    /**
     * 查询最近n天涨幅的基金
     *
     * @param date
     * @param n
     * @return
     */
    List<FundVo> lastNRise(Date date, int n, int sortType, boolean continuation);
}

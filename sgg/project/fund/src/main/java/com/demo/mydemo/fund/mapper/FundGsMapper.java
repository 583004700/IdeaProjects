package com.demo.mydemo.fund.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.mydemo.fund.entity.po.FundGsPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FundGsMapper extends BaseMapper<FundGsPo> {
    int insertBatch(@Param("list") List<FundGsPo> funds);
}

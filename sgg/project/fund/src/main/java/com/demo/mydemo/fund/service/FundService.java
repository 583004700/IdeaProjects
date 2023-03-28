package com.demo.mydemo.fund.service;

import com.demo.mydemo.fund.entity.Fund;

import java.util.List;
import java.util.Map;

public interface FundService {
    Map<String, Fund> getAllFund();

    Fund getFundByCode(Fund fund);

    List<Fund> getGszSort();
}

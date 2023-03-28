package com.demo.mydemo.fund.controller;

import com.demo.mydemo.fund.entity.Fund;
import com.demo.mydemo.fund.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/fund")
public class FundController {

    @Autowired
    FundService fundService;

    /**
     * 获取所有基金实时估算值排序
     *
     * @return
     */
    @RequestMapping("/getGszSort")
    @ResponseBody
    public List<Fund> getGszSort() {
        return fundService.getGszSort();
    }
}

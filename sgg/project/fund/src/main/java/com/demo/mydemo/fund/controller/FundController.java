package com.demo.mydemo.fund.controller;

import com.demo.mydemo.fund.entity.Fund;
import com.demo.mydemo.fund.entity.po.FundGsPo;
import com.demo.mydemo.fund.entity.vo.FundVo;
import com.demo.mydemo.fund.service.FundService;
import com.demo.mydemo.fund.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Date;
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

    @RequestMapping("/insertBatch")
    @ResponseBody
    public int insertBatch() {
        return fundService.insertBatch();
    }

    @RequestMapping("/lastNRise")
    @ResponseBody
    public List<FundVo> lastNRise(@RequestParam("date") String date, @RequestParam("n") int n) throws ParseException {
        return fundService.lastNRise(DateUtil.parse(DateUtil.yyyy_MM_dd, date), n);
    }
}

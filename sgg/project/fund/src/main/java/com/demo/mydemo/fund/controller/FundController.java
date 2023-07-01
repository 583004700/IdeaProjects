package com.demo.mydemo.fund.controller;

import com.demo.mydemo.fund.entity.Fund;
import com.demo.mydemo.fund.entity.po.FundGsPo;
import com.demo.mydemo.fund.entity.vo.FundVo;
import com.demo.mydemo.fund.entity.vo.common.PageVo;
import com.demo.mydemo.fund.entity.vo.common.ResultVo;
import com.demo.mydemo.fund.service.FundService;
import com.demo.mydemo.fund.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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

    @RequestMapping("/insertBatchHistory")
    @ResponseBody
    public int insertBatchHistory(@RequestParam("gzdate") String date) throws ParseException {
        return fundService.insertBatchHistory(DateUtil.parse(DateUtil.yyyy_MM_dd,date));
    }

    @RequestMapping("/insertBatch")
    @ResponseBody
    public int insertBatch() {
        return fundService.insertBatch();
    }

    @RequestMapping("/lastNRise")
    @ResponseBody
    public ResultVo<PageVo<FundVo>> lastNRise(@RequestParam("gzdate") String date, @RequestParam("n") int n,
                                              @RequestParam(value = "sortType", required = false) Integer sortType,
                                              @RequestParam(value = "page", required = false) Integer page,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                              @RequestParam(value = "field", required = false) String field,
                                              @RequestParam(value = "continuation",required = false)Boolean continuation,
                                              @RequestParam(value = "fundName", required = false) String fundName) throws ParseException {
        if (sortType == null) {
            sortType = 2;
        }
        if (!StringUtils.isEmpty(field)) {
            if (field.equals("nDaysGszzl")) {
                sortType = 2;
            } else if (field.equals("gszzl")) {
                sortType = 1;
            }
        }
        if(continuation == null){
            continuation = true;
        }
        List<FundVo> result = fundService.lastNRise(DateUtil.parse(DateUtil.yyyy_MM_dd, date), n, sortType,continuation,fundName);
        PageVo<FundVo> pageVo = new PageVo<>();
        int total = result.size();
        pageVo.setTotal(total);
        if (page != null && pageSize != null && total > 0) {
            int start = (page - 1) * pageSize;
            start = Math.min(start, total - 1);
            int end = start + pageSize;
            end = Math.min(end, total - 1);
            result = result.subList(start, end);
        }
        pageVo.setItems(result);
        ResultVo<PageVo<FundVo>> resultVo = new ResultVo<>();
        resultVo.setResult(pageVo);
        return resultVo;
    }
}

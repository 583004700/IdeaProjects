package com.demo.mydemo.fund.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.mydemo.fund.entity.Fund;
import com.demo.mydemo.fund.entity.po.FundGsPo;
import com.demo.mydemo.fund.entity.vo.FundVo;
import com.demo.mydemo.fund.mapper.FundGsMapper;
import com.demo.mydemo.fund.utils.DateUtil;
import com.demo.mydemo.fund.utils.HttpClientUtil;
import com.xxl.job.core.context.XxlJobHelper;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class FundServiceImpl implements FundService {

    private int historySleepTime = Integer.valueOf(System.getProperty("historySleepTime"));

    @Setter
    public static class FundTask implements Callable<List<Fund>> {
        private FundService fundService;
        private List<Fund> funds;

        private int sleepTime = Integer.valueOf(System.getProperty("sleepTime"));

        public FundTask(FundService fundService) {
            this.fundService = fundService;
        }

        @Override
        public List<Fund> call() throws Exception {
            List<Fund> result = new ArrayList<>();
            for (int i = 0; i < funds.size(); i++) {
                Fund f = funds.get(i);
                Fund fundByCode = fundService.getFundByCode(f);
                if (fundByCode.getGszzl() != null) {
                    result.add(fundByCode);
                }
                Thread.sleep(sleepTime);
            }
            return result;
        }
    }

    @Autowired
    HttpClientUtil httpClientUtil;

    @Autowired
    FundGsMapper fundGsMapper;

    private final String allFundUrl = "http://fund.eastmoney.com/js/fundcode_search.js";

    private final String singleFundUrl = "http://fundgz.1234567.com.cn/js/${code}.js?rt=1463558676006";

    private final String historyUrl = "http://fundf10.eastmoney.com/F10DataApi.aspx?type=lsjz&code=${code}&page=1&sdate=${sdate}&edate=${edate}&per=1";

    private final static int THREAD_NUM = 9;

    private ExecutorService executorService;

    private ReentrantLock lock = new ReentrantLock();

    private Long historyGszSortLastExecTime = 0L;
    private Long historyExecMaxTime = Long.valueOf(System.getProperty("historyExecMaxTime"));

    public FundServiceImpl() {
        this.executorService = Executors.newFixedThreadPool(this.THREAD_NUM);
    }

    public Map<String, Fund> getAllFund() {
        byte[] allFundStr = httpClientUtil.getForEntity(allFundUrl, byte[].class);
        String body = new String(allFundStr, StandardCharsets.UTF_8);
        String arrStr = body.split("=")[1];
        arrStr = arrStr.substring(0, arrStr.length() - 1);
        JSONArray allFundJsonArr = JSONArray.parseArray(arrStr);
        Map<String, Fund> result = new HashMap<>();
        for (int i = 0; i < allFundJsonArr.size(); i++) {
            JSONArray singleFundArr = allFundJsonArr.getJSONArray(i);
            Fund fund = new Fund();
            fund.setFundcode(singleFundArr.getString(0));
            fund.setName(singleFundArr.getString(2));
            fund.setType(singleFundArr.getString(3));
            result.put(fund.getFundcode(), fund);
        }
        return result;
    }

    public Fund getHistoryFundByCode(Fund fund, Date date) {
        boolean hasHis = true;
        String dateStr = DateUtil.format(DateUtil.yyyy_MM_dd, date);
        String smDateStr = DateUtil.format(DateUtil.yyyy_MM_dd, DateUtil.subMonth(date, 1));
        String hisUrl = historyUrl.replace("${code}", fund.getFundcode())
                .replace("${sdate}", smDateStr)
                .replace("${edate}", dateStr);
        byte[] hisFundStr = httpClientUtil.getForEntity(hisUrl, byte[].class);
        if (hisFundStr != null) {
            String body = new String(hisFundStr, StandardCharsets.UTF_8);
            if (body.contains("暂无数据")) {
                hasHis = false;
            }
            if (hasHis) {
                body = body.replace("var apidata=", "").replace(";", "");
                String content = JSONObject.parseObject(body).getString("content");
                Pattern pattern = Pattern.compile("-?\\d*\\.*\\d*%");
                Matcher matcher = pattern.matcher(content);
                String gszzl = null;
                if (matcher.find()) {
                    gszzl = matcher.group(0);
                }
                Pattern timePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
                Matcher timeMatcher = timePattern.matcher(content);
                String gzdate = null;
                if (timeMatcher.find()) {
                    gzdate = timeMatcher.group(0);
                }
                if (!StringUtils.isEmpty(gszzl) && !StringUtils.isEmpty(gzdate)) {
                    gszzl = gszzl.replace("%", "");
                    fund.setGszzl(BigDecimal.valueOf(Double.parseDouble(gszzl)));
                    fund.setGztime(gzdate + " 15:00:00");
                }
            }
        }
        return fund;
    }

    public Fund getFundByCode(Fund fund) {
        String url = singleFundUrl.replace("${code}", fund.getFundcode());
        String body = HttpClientUtil.get(url);
        if (body != null) {
            String fundJsonStr = body.replace("jsonpgz(", "").replace(");", "");
            JSONObject fundJsonObject = JSONObject.parseObject(fundJsonStr);
            if (fundJsonObject != null) {
                String gszzl = fundJsonObject.getString("gszzl");
                if (!StringUtils.isEmpty(gszzl)) {
                    fund.setGszzl(fundJsonObject.getBigDecimal("gszzl"));
                    fund.setGztime(fundJsonObject.getString("gztime"));
                }
            }
        }
        return fund;
    }

    public List<Fund> getHistoryGszSort(Date date) {
        boolean can = false;
        synchronized (this) {
            long subTime = System.currentTimeMillis() - historyGszSortLastExecTime;
            System.out.println("subTime:" + subTime);
            System.out.println("historyGszSortLastExecTime:" + historyGszSortLastExecTime);
            if (subTime > historyExecMaxTime) {
                historyGszSortLastExecTime = System.currentTimeMillis();
                can = true;
            }
        }
        List<Fund> result = new ArrayList<>();
        try {
            if (can) {
                Map<String, Fund> allFund = this.getAllFund();
                Iterator<Map.Entry<String, Fund>> iterator = allFund.entrySet().iterator();
                while (iterator.hasNext()) {
                    try {
                        Map.Entry<String, Fund> next = iterator.next();
                        Fund historyFundByCode = getHistoryFundByCode(next.getValue(), date);
                        if (historyFundByCode.getGszzl() != null) {
                            result.add(historyFundByCode);
                            XxlJobHelper.log("已经获取" + result.size() + "条具体估值数据！");
                        }
                        Thread.sleep(historySleepTime);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Fund> getGszSort() {
        List<Fund> result = new ArrayList<>();
        boolean can = false;
        try {
            can = lock.tryLock();
            if (can) {
                Map<String, Fund> allFund = this.getAllFund();
                Iterator<Map.Entry<String, Fund>> iterator = allFund.entrySet().iterator();
                List<Callable<List<Fund>>> callables = new ArrayList<>();
                List<List<Fund>> threadParams = new ArrayList<>();
                for (int i = 0; i < this.THREAD_NUM; i++) {
                    List<Fund> threadParam = new ArrayList<>();
                    threadParams.add(threadParam);
                }

                int index = 0;
                while (iterator.hasNext()) {
                    Map.Entry<String, Fund> next = iterator.next();
                    threadParams.get(index % this.THREAD_NUM).add(next.getValue());
                    index++;
                }

                for (int i = 0; i < threadParams.size(); i++) {
                    FundTask fundTask = new FundTask(this);
                    fundTask.setFunds(threadParams.get(i));
                    callables.add(fundTask);
                }

                List<Future<List<Fund>>> futures = null;

                futures = this.executorService.invokeAll(callables);
                for (int i = 0; i < futures.size(); i++) {
                    Future<List<Fund>> future = futures.get(i);
                    List<Fund> funds = future.get();
                    result.addAll(funds);
                }
                result.sort((a, b) -> {
                    if (b.getGszzl().equals(a.getGszzl())) {
                        return 0;
                    }
                    return b.getGszzl().compareTo(a.getGszzl());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (can) {
                lock.unlock();
            }
        }
        return result;
    }

    public int insertBatchHistory(Date date) {
        List<Fund> gszSort = getHistoryGszSort(date);
        if (gszSort == null || gszSort.isEmpty()) {
            return 0;
        }
        List<FundGsPo> fundGsPos = new ArrayList<>();
        gszSort.forEach(t -> {
            FundGsPo fundGsPo = new FundGsPo();
            BeanUtils.copyProperties(t, fundGsPo);
            String oldTime = t.getGztime();
            try {
                fundGsPo.setGztime(DateUtil.parse(DateUtil.yyyy_MM_dd_HH_mm, oldTime));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if (!StringUtils.isEmpty(oldTime)) {
                fundGsPo.setGzdate(oldTime.replaceAll("-", "").substring(0, 8));
            }
            fundGsPo.setUpdatedTime(new Date());
            fundGsPos.add(fundGsPo);
        });
        if (!fundGsPos.isEmpty()) {
            return fundGsMapper.insertBatch(fundGsPos);
        } else {
            return 0;
        }
    }

    public int insertBatch() {
        List<Fund> gszSort = getGszSort();
        if (gszSort == null || gszSort.isEmpty()) {
            return 0;
        }
        List<FundGsPo> fundGsPos = new ArrayList<>();
        gszSort.forEach(t -> {
            FundGsPo fundGsPo = new FundGsPo();
            BeanUtils.copyProperties(t, fundGsPo);
            String oldTime = t.getGztime();
            try {
                fundGsPo.setGztime(DateUtil.parse(DateUtil.yyyy_MM_dd_HH_mm, oldTime));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if (!StringUtils.isEmpty(oldTime)) {
                fundGsPo.setGzdate(oldTime.replaceAll("-", "").substring(0, 8));
            }
            if (fundGsPo.getGztime() != null
                    && DateUtil.format(DateUtil.yyyy_MM_dd, new Date())
                    .equals(DateUtil.format(DateUtil.yyyy_MM_dd, fundGsPo.getGztime()))) {
                fundGsPo.setUpdatedTime(new Date());
                fundGsPos.add(fundGsPo);
            }
        });
        if (!fundGsPos.isEmpty()) {
            return fundGsMapper.insertBatch(fundGsPos);
        } else {
            return 0;
        }
    }

    @Override
    public List<FundGsPo> selectList(Map<String, Object> param) {
        LambdaQueryWrapper<FundGsPo> queryWrapper = new QueryWrapper<FundGsPo>().lambda();
        if (param.containsKey("gzdate")) {
            queryWrapper.eq(FundGsPo::getGzdate, param.get("gzdate"));
        }
        if(!ObjectUtils.isEmpty(param.get("fundName"))){
            queryWrapper.like(FundGsPo::getName,param.get("fundName"));
        }
        queryWrapper.last(" and fundcode not in (\n" +
                "    select fundcode from fund_a\n" +
                ") order by gzdate desc,gszzl desc");
        return fundGsMapper.selectList(queryWrapper);
    }

    @Override
    public List<FundVo> lastNRise(Date date, int n, int sortType, boolean continuation,String fundName) {
        int count = 0;
        int realCount = 0;
        int maxCount = 100;
        List<Map<String, FundGsPo>> allFundGsPos = new ArrayList<>();
        int minLength = Integer.MIN_VALUE;
        Map<String, FundGsPo> maxMap = new HashMap<>();
        while (realCount < n && count < maxCount) {
            Map<String, Object> param = new HashMap<>();
            param.put("gzdate", DateUtil.format("yyyyMMdd", date));
            param.put("fundName",fundName);
            List<FundGsPo> fundGsPos = selectList(param);
            if (fundGsPos != null && !fundGsPos.isEmpty()) {
                realCount++;
                Map<String, FundGsPo> collect = fundGsPos.stream().collect(Collectors.toMap(FundGsPo::getFundcode, f -> f));
                allFundGsPos.add(collect);
                if (collect.size() > minLength) {
                    minLength = collect.size();
                    maxMap = collect;
                }
            }
            count++;
            date = DateUtil.subDate(date, 1);
        }

        List<FundVo> result = new ArrayList<>();
        maxMap.forEach((k, v) -> {
            BigDecimal nDaysGszzl = BigDecimal.valueOf(1);
            boolean flag = true;
            for (int i = 0; i < allFundGsPos.size(); i++) {
                Map<String, FundGsPo> every = allFundGsPos.get(i);
                FundGsPo fundGsPo = every.get(k);
                if(fundGsPo == null){
                    fundGsPo = new FundGsPo();
                    every.put(k,fundGsPo);
                }
                if(fundGsPo.getGszzl() == null){
                    fundGsPo.setGszzl(new BigDecimal("0"));
                }
                if (continuation) {
                    if (fundGsPo.getGszzl().doubleValue() < 0) {
                        flag = false;
                        break;
                    }
                }
                BigDecimal xsGszzl = fundGsPo.getGszzl().divide(BigDecimal.valueOf(100)).add(BigDecimal.valueOf(1));
                nDaysGszzl = nDaysGszzl.multiply(xsGszzl);
            }
            nDaysGszzl = nDaysGszzl.subtract(BigDecimal.valueOf(1));
            nDaysGszzl = nDaysGszzl.multiply(BigDecimal.valueOf(100));
            Map<String, FundGsPo> lastMap = allFundGsPos.get(0);
            if (flag && lastMap != null) {
                FundVo fundVo = new FundVo();
                fundVo.setNDaysGszzl(nDaysGszzl);
                BeanUtils.copyProperties(v, fundVo);
                result.add(fundVo);
            }
            if (sortType == 1) {
                // 最近一天涨幅最大
                result.sort((a, b) -> {
                    int c = b.getGszzl().compareTo(a.getGszzl());
                    if (c != 0) {
                        return c;
                    } else {
                        return b.getNDaysGszzl().compareTo(a.getNDaysGszzl());
                    }
                });
            } else {
                // 最近n天涨幅最大
                result.sort((a, b) -> {
                    int c = b.getNDaysGszzl().compareTo(a.getNDaysGszzl());
                    if (c != 0) {
                        return c;
                    } else {
                        return b.getGszzl().compareTo(a.getGszzl());
                    }
                });
            }
        });
        return result;
    }
}

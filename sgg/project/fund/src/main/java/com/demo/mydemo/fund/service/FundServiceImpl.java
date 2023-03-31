package com.demo.mydemo.fund.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.mydemo.fund.entity.Fund;
import com.demo.mydemo.fund.utils.HttpClientUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class FundServiceImpl implements FundService {

    @Setter
    public static class FundTask implements Callable<List<Fund>> {
        private FundService fundService;
        private List<Fund> funds;
        private int sleepTime = 40;

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

    private final String allFundUrl = "http://fund.eastmoney.com/js/fundcode_search.js";

    private final String singleFundUrl = "http://fundgz.1234567.com.cn/js/${code}.js?rt=1463558676006";

    private final static int THREAD_NUM = 9;

    private ExecutorService executorService;

    private ReentrantLock lock = new ReentrantLock();

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

    public Fund getFundByCode(Fund fund) {
        String url = singleFundUrl.replace("${code}", fund.getFundcode());
        byte[] fundStr = httpClientUtil.getForEntity(url, byte[].class);
        if (fundStr != null) {
            String body = new String(fundStr, StandardCharsets.UTF_8);
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

}

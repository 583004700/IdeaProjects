package com.demo.mydemo.fund.task;

import com.demo.mydemo.fund.service.FundService;
import com.demo.mydemo.fund.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
@EnableAsync
public class FundTask {

    @Autowired
    FundService fundService;

    @Scheduled(cron = "0 20 10 ? * MON-FRI")
    @Async
    @Lazy(value = false)
    public void insertBatchHistory() {
        Date currentDate = new Date();
        System.out.println("insertBatchHistory定时任务启动：当前时间为：" + DateUtil.format(DateUtil.yyyy_MM_dd_HH_mm, currentDate));
        int row = fundService.insertBatchHistory(DateUtil.subDate(currentDate, 1));
        System.out.println("insertBatchHistory定时任务完成：当前时间为："
                + DateUtil.format(DateUtil.yyyy_MM_dd_HH_mm, new Date()) + ";row为：" + row);
    }
}

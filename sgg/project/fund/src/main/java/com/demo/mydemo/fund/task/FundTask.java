package com.demo.mydemo.fund.task;

import com.demo.mydemo.fund.service.FundService;
import com.demo.mydemo.fund.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
public class FundTask {

    @Autowired
    FundService fundService;

    @Scheduled(cron = "0 40 11,14,15,17 * * MON-FRI")
    private void insertBatch() {
        fundService.insertBatch();
    }

    @Scheduled(cron = "0 20 10 * * MON-FRI")
    private void insertBatchHistory() {
        fundService.insertBatchHistory(DateUtil.subDate(new Date(), 1));
    }
}

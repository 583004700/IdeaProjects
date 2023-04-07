package com.demo.mydemo.fund.task;

import com.demo.mydemo.fund.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class FundTask {

    @Autowired
    FundService fundService;

    @Scheduled(cron = "0 40 11,14,15,17 * * MON-FRI")
    private void insertBatch() {
        fundService.insertBatch();
    }
}

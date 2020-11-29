package com.atguigu.task.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

    /**
     * second,minute,hour,day of month,month,day of week.
     * 0 * * * * MON-FRI  周一到周五每分钟一次
     * 定时任务，需要开启异步注解@EnableScheduling
     */
    //秒数到0时会执行
    //@Scheduled(cron = "0 * * * * *")
    //秒数到 0,2,3,4 时都会运行
    //@Scheduled(cron = "0,1,2,3,4 * * * * *")
    //@Scheduled(cron = "0-4 * * * * *")
    //  /代表步长，每4秒执行一次
    @Scheduled(cron = "0/4 * * * * *")
    public void hello(){
        System.out.println("hello...");
    }

}

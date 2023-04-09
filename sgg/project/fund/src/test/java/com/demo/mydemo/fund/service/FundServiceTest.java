package com.demo.mydemo.fund.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FundServiceTest {
    @Autowired
    FundService fundService;

    @Test
    public void testInsertBatch() {
        int row = fundService.insertBatch();
        System.out.println("row:" + row);
    }

    @Test
    public void testInsertBatchHistory() {
        int row = fundService.insertBatchHistory(new Date());
        System.out.println("row:" + row);
    }
}

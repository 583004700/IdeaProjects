package com.demo.mydemo.fund.mapper;

import com.demo.mydemo.fund.entity.po.FundGsPo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FundGsMapperTest {
    @Autowired
    FundGsMapper fundGsMapper;

    @Test
    public void testInsertBatch() {
        List<FundGsPo> list = new ArrayList<>();
        FundGsPo p1 = new FundGsPo();
        p1.setFundcode("001");
        p1.setName("一");
        p1.setGztime("20230101");

        FundGsPo p2 = new FundGsPo();
        p2.setFundcode("002");
        p2.setName("三");
        p2.setGztime("20230102");
        p2.setType("三type");
        list.add(p1);
        list.add(p2);
        fundGsMapper.insertBatch(list);
    }
}

package com.demo.mr.flowsum;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class ProvincePartitioner extends Partitioner<Text,FlowBean> {
    @Override
    public int getPartition(Text key, FlowBean flowBean, int i) {
        //key 是手机号
        //value 流量信息

        String prePhoneNum = key.toString().substring(0,3);
        int partition = 4;
        if("136".equals(prePhoneNum)){
            partition = 0;
        }
        if("137".equals(prePhoneNum)){
            partition = 1;
        }
        if("138".equals(prePhoneNum)){
            partition = 2;
        }if("139".equals(prePhoneNum)){
            partition = 3;
        }
        return partition;
    }
}
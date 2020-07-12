package com.demo.mr.flowsum;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowCountMapper extends Mapper<LongWritable, Text,Text,FlowBean> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1	13736230513	192.196.100.1	www.atguigu.com	2481	24681	200
        String line = value.toString();
        String[] fields = line.split("\t");
        Text k = new Text(fields[1]);
        FlowBean v = new FlowBean(Long.valueOf(fields[fields.length-3]),Long.valueOf(fields[fields.length-2]));
        context.write(k,v);
    }
}

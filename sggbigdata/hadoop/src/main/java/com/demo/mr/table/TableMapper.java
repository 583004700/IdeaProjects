package com.demo.mr.table;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class TableMapper extends Mapper<LongWritable, Text,Text,TableBean> {
    TableBean tableBean = new TableBean();
    String name;
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // 获取文件名称
        FileSplit inputSplit = (FileSplit) context.getInputSplit();
        name = inputSplit.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // id pid amount
        // 1001 01 1
        //pid pname
        //01 小米
        Text k = new Text();
        String line = value.toString();
        String[] fields = line.split("\t");
        if(name.startsWith("order")){//订单表
            tableBean.setId(fields[0]);
            tableBean.setPid(fields[1]);
            tableBean.setAmount(Integer.parseInt(fields[2]));
            tableBean.setPName("");
            tableBean.setFlag("order");
            k.set(fields[1]);
        }else{//产品表
            tableBean.setId("");
            tableBean.setPid(fields[0]);
            tableBean.setAmount(0);
            tableBean.setPName(fields[1]);
            tableBean.setFlag("pd");
            k.set(fields[0]);
        }
        context.write(k,tableBean);
    }
}

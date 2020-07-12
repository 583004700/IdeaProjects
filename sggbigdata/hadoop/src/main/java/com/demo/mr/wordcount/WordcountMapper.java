package com.demo.mr.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

// 输入数据key 输入数据value 输出数据key 输出数据value
public class WordcountMapper extends Mapper<LongWritable, Text,Text, IntWritable> {
    /**
     *
     * @param key 行的偏移量
     * @param value 行的值
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        Text k = new Text();
        IntWritable v = new IntWritable(1);
        String line = value.toString();
        String[] words = line.split(" ");
        for(String word : words){
            k.set(word);
            context.write(k,v);
        }
        System.out.println("map_this:"+this);
        System.out.println("map_Thread:"+Thread.currentThread());
    }
}

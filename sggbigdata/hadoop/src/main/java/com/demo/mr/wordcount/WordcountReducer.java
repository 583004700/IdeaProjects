package com.demo.mr.wordcount;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


public class WordcountReducer extends Reducer<Text, IntWritable,Text,IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;

        for(IntWritable value : values){
            sum += value.get();
            System.out.println("-----------"+key+":"+value);
        }

        context.write(key,new IntWritable(sum));

        System.out.println("reducer_this:"+this);
        System.out.println("reducer_Thread:"+Thread.currentThread());
    }
}

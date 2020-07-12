package com.demo.mr.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 对每个mapTask局部汇总，减少网络传输,比如默认情况下如果使用TextInputFormat有多个文件，则有多个mapTask，每个文件中的数量
 * 统计一次再给reducer
 */
public class WordcountCombiner extends Reducer<Text, IntWritable,Text,IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for(IntWritable value : values){
            sum += value.get();
        }
        IntWritable v = new IntWritable();
        v.set(sum);
        context.write(key,v);
    }
}

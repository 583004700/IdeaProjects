package com.demo.mr.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordcountDriver {
    public static void main(String[] args) throws Exception{
        try {
            // 设置 HADOOP_HOME 目录
            System.setProperty("hadoop.home.dir", "C:/Program Files/hadoop-2.7.2");
            // 加载库文件
            System.load("C:/Program Files/hadoop-2.7.2/bin/hadoop.dll");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(1);
        }

        args = new String[2];
        args[0] = "d:/bigdata/hadoop/input";
        args[1] = "d:/bigdata/hadoop/output";
        // 1 获取Job对象
        Configuration conf = new Configuration();
        //开启map端输出压缩
        conf.setBoolean("mapreduce.map.output.compress",true);
        conf.setClass("mapreduce.map.output.compress.codec", BZip2Codec.class, CompressionCodec.class);
        Job job = Job.getInstance(conf);
        // 2 设置jar存储位置
        job.setJarByClass(WordcountDriver.class);
        // 3 关联map和reduce类
        job.setMapperClass(WordcountMapper.class);
        job.setReducerClass(WordcountReducer.class);
        // 4 设置Mapper阶段输出数据的key和value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 5 设置最终数据输出的key和value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 如果不设置InputFormat，它默认用的是TextInputFormat.class，如果有多个小文件，每个文件都产生一个mapTask
        //job.setInputFormatClass(CombineTextInputFormat.class);
        //虚拟存储切片最大值设置4m，多个小文件合成一次mapTask
        //CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);

        job.setCombinerClass(WordcountCombiner.class); //或者可以直接用 WordCountReducer.class,因为代码逻辑一样

        //设置reduce端输出压缩开启
        FileOutputFormat.setCompressOutput(job,true);
        //设置压缩方式
        FileOutputFormat.setOutputCompressorClass(job,BZip2Codec.class);

        // 6 设置输入路径和输出路径
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));
        // 7 提交
        //job.submit();
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);

        //hadoop jar /bigdata/hadoop/mr.jar /wcinput /wcoutput
    }
}

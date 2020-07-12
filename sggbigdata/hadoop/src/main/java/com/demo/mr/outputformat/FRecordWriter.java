package com.demo.mr.outputformat;

import java.io.IOException;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class FRecordWriter extends RecordWriter<Text, NullWritable> {
	
	FSDataOutputStream fosatguigu;
	FSDataOutputStream fosother;
	
	public FRecordWriter(TaskAttemptContext job) {
		
		try {
			// 1 获取文件系统
			FileSystem fs = FileSystem.get(job.getConfiguration());
			
			// 2 创建输出到atguigu.log的输出流
			fosatguigu = fs.create(new Path("e:/atguigu.log"));
			
			// 3 创建输出到other.log的输出流
			fosother = fs.create(new Path("e:/other.log"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void write(Text key, NullWritable value) throws IOException, InterruptedException {
		// 判断key当中是否有atguigu,如果有写出到atguigu.log  如果没有写出到other.log
		
		if (key.toString().contains("atguigu")) {
			// atguigu输出流
			fosatguigu.write(key.toString().getBytes());
		}else {
			fosother.write(key.toString().getBytes());
		}
	}

	@Override
	public void close(TaskAttemptContext context) throws IOException, InterruptedException {
		
		IOUtils.closeStream(fosatguigu);
		IOUtils.closeStream(fosother);
	}
}

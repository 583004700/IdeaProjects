package com.demo.mydemo.wc

import org.apache.flink.api.scala._;

//批处理 word count程序
object WordCount {
  def main(args: Array[String]): Unit = {
    //创建一个执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment;
    //从文件中读取数据
    val inputPath = "E:\\IdeaProjects\\sggbigdata\\flink\\src\\main\\resources\\hello.txt";
    val inputDataSet = env.readTextFile(inputPath);
    //切分数据得到word,然后再按word作分组聚合
    val wordCountDataSet = inputDataSet.flatMap(_.split(" "))
      .map((_,1)).groupBy(0).sum(1);
    wordCountDataSet.print();
  }
}

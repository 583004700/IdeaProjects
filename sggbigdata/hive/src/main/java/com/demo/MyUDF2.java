package com.demo;

import org.apache.hadoop.hive.ql.exec.UDF;

public class MyUDF2 extends UDF {

    public int evaluate(int data){
        return data + 5;
    }

    public int evaluate(int data,int data2){
        return data + data2 + 5;
    }

}

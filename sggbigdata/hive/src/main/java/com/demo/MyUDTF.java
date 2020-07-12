package com.demo;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;
import java.util.List;

public class MyUDTF extends GenericUDTF {
    private List<String> dataList = new ArrayList<String>();

    //定义输出数据的列名和数据类型
    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        //定义输出数据的列名
        List<String> fieldNames = new ArrayList<String>();
        //定义数据的类型
        List<ObjectInspector> fieldOIs = new ArrayList<ObjectInspector>();
        fieldNames.add("word");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames,fieldOIs);
    }

    @Override
    public void process(Object[] args) throws HiveException {
        //1.获取数据
        String data = args[0].toString();
        //2.获取分隔符
        String splitKey = args[1].toString();
        //3.切分数据
        String[] words = data.split(splitKey);
        //4.遍历写出
        for(String word : words){
            dataList.clear();
            dataList.add(word);
            //5.写出数据的操作
            forward(dataList);
        }
    }

    @Override
    public void close() throws HiveException {

    }
}

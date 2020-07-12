package com.demo.mr.flowsum;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowCountReducer extends Reducer<Text,FlowBean,Text,FlowBean> {
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        // 1 累加求各
        long sumUpFlow = 0;
        long sumDownFlow = 0;
        for(FlowBean flowBean : values){
            sumUpFlow += flowBean.getUpFlow();
            sumDownFlow += flowBean.getDownFlow();
        }
        FlowBean v = new FlowBean(sumUpFlow,sumDownFlow);
        context.write(key,v);
    }
}

package com.demo.mr.table;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TableReducer extends Reducer<Text,TableBean,TableBean, NullWritable> {
    @Override
    protected void reduce(Text key, Iterable<TableBean> values, Context context) throws IOException, InterruptedException {
        // 存储所有订单集合
        ArrayList<TableBean> orderBeans = new ArrayList<TableBean>();
        TableBean pdBean = new TableBean();
        for(TableBean tableBean : values){
            TableBean bean = new TableBean();
            try {
                BeanUtils.copyProperties(bean,tableBean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if("order".equals(tableBean.getFlag())){
                orderBeans.add(bean);
            }else{
                pdBean = bean;
            }
        }
        for (TableBean tableBean : orderBeans){
            tableBean.setPName(pdBean.getPName());
            context.write(tableBean,NullWritable.get());
        }
    }
}

package com.demo.mydemo.mp5.beans.metaObjectHandler;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

public class MyMetaObjectHandler extends MetaObjectHandler {
    /**
     * 插入操作 自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //获取到需要被填充的字段的值
        Object fieldValue = getFieldValByName("name",metaObject);
        if(fieldValue == null){
            System.out.println("*****插入操作满足填充条件*****");
            setFieldValByName("name","没有名字时填充",metaObject);
        }
    }

    /**
     * 修改操作 自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        //获取到需要被填充的字段的值
        Object fieldValue = getFieldValByName("name",metaObject);
        if(fieldValue == null){
            System.out.println("*****插入操作满足填充条件*****");
            setFieldValByName("name","名字",metaObject);
        }
    }
}

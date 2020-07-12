package com.atguigu.bean;

import org.springframework.beans.factory.FactoryBean;

public class ColorFactoryBean implements FactoryBean<Color>{
    public Color getObject() throws Exception {
        return new Color();
    }

    public Class<?> getObjectType() {
        return Color.class;
    }
    //是否是单例
    //true这个bean是单实例，在容器中保存一份
    public boolean isSingleton() {
        return true;
    }
}

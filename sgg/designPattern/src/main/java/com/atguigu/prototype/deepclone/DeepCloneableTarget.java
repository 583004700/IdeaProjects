package com.atguigu.prototype.deepclone;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class DeepCloneableTarget implements Serializable,Cloneable {
    private String cloneName;
    private String cloneClass;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

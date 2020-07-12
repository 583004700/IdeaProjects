package com.atguigu.prototype.deepclone;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
        DeepCloneableTarget deepCloneableTarget = new DeepCloneableTarget("大牛","大牛类");
        DeepProtoType deepProtoType = new DeepProtoType();
        deepProtoType.name="name";
        deepProtoType.deepCloneableTarget = deepCloneableTarget;

        //DeepProtoType deepProtoType1 = (DeepProtoType) deepProtoType.clone();
        DeepProtoType deepProtoType1 = (DeepProtoType) deepProtoType.deepClone();
        System.out.println(deepProtoType.name);
        System.out.println(deepProtoType1.name);
        System.out.println(deepProtoType.deepCloneableTarget == deepProtoType1.deepCloneableTarget);
    }
}

package com.atguigu.prototype.improve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Sheep implements Cloneable{
    private String name;
    private int age;
    private String color;

    private Sheep friend;

    //克隆该实例，使用默认的clone方法来完成
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

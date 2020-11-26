package com.demo.mydemo.mp5.beans;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@Getter
@Setter
@Accessors(chain = true)

//可以放在父类中，实现多个类共用一个序列
//@KeySequence(value = "seq_user",clazz = Integer.class)
public class User extends Parent{
    @TableId(type= IdType.INPUT)
    private Integer id;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String name;
    @TableLogic //逻辑删除属性
    private Integer logicFlag;
}

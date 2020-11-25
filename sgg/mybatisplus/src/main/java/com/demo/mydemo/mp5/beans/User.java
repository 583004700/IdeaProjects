package com.demo.mydemo.mp5.beans;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@Getter
@Setter
@Accessors(chain = true)
public class User {
    private Integer id;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String name;
    @TableLogic //逻辑删除属性
    private Integer logicFlag;
}

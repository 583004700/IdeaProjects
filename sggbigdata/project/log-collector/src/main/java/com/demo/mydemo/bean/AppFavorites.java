package com.demo.mydemo.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 收藏
 */
@Setter
@Getter
public class AppFavorites {
    private int id;//主键
    private int course_id;//商品id
    private int userid;//用户ID
    private String add_time;//创建时间
}

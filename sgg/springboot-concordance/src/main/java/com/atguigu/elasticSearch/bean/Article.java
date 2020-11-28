package com.atguigu.elasticSearch.bean;

import io.searchbox.annotations.JestId;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class Article {
    @JestId
    private Integer id;
    private String author;
    private String title;
    private String content;
}

package com.atguigu.elasticSearch.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;

@Setter
@Getter
@Accessors(chain = true)
@ToString
@Document(indexName = "atguigu",type = "book")
public class Book {
    private Integer id;
    private String bookName;
    private String author;
}

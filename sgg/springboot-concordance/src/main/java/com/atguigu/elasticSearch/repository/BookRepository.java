//package com.atguigu.elasticSearch.repository;
//
//import com.atguigu.elasticSearch.bean.Book;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
//import java.util.List;
//
//public interface BookRepository extends ElasticsearchRepository<Book,Integer> {
//
//    //可以参照官方文档的命名方式
//    List<Book> findByBookNameLike(String bookName);
//
//}

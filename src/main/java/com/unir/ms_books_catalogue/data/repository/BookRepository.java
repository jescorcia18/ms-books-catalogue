package com.unir.ms_books_catalogue.data.repository;

import com.unir.ms_books_catalogue.data.model.Book;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

//public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
@Repository
public interface BookRepository extends ElasticsearchRepository<Book, String> {
}

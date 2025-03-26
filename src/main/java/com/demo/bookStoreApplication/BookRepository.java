package com.demo.bookStoreApplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}

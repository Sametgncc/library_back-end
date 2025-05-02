package com.example.repository.business;

import com.example.entity.concretes.business.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}

package com.example.repository.business;

import com.example.entity.concretes.business.FavoriteBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteBookRepository extends JpaRepository<FavoriteBook, Long> {

    Optional<FavoriteBook> findByUserIdAndBookId(Long userId, Long bookId);
    List<FavoriteBook> findByUserId(Long userId);
    void deleteByUserIdAndBookId(Long userId, Long bookId);
}


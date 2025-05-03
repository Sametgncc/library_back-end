package com.example.repository.business;

import com.example.entity.concretes.business.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {

    List<Borrow> findByUserId(Long userId);

    List<Borrow> findByBookId(Long bookId);

    List<Borrow> findByIsReturnedFalse();

    @Query("SELECT b FROM Borrow b WHERE b.user.id = :userId AND b.isReturned = false")
    List<Borrow> findActiveBooksByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(b) FROM Borrow b WHERE b.user.id = :userId AND b.isReturned = false")
    int countActiveBooksByUserId(@Param("userId") Long userId);
}
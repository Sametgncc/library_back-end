package com.example.repository.business;

import com.example.entity.concretes.business.Borrow;
import com.example.entity.concretes.business.BorrowHistory;
import com.example.entity.concretes.business.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PaymentsRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findByBookId(Long bookId);
}

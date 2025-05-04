package com.example.repository.business;

import com.example.entity.concretes.business.Borrow;
import com.example.entity.concretes.business.BorrowHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowHistoryRepository extends JpaRepository<BorrowHistory, Long> {
    List<BorrowHistory> findByBorrowId(Long borrowId);
}

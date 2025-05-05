package com.example.repository.business;

import com.example.entity.concretes.business.Payments;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PaymentsRepository extends JpaRepository<Payments, Long> {



}

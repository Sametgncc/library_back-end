package com.example.service.business;

import com.example.entity.concretes.business.Borrow;
import com.example.entity.concretes.business.Payments;
import com.example.payload.business.ResponseMessage;
import com.example.payload.request.business.BorrowRequest;
import com.example.payload.request.business.PaymentsRequest;
import com.example.payload.response.business.BorrowResponse;
import com.example.payload.response.business.PaymentsResponse;
import com.example.repository.business.BorrowRepository;
import com.example.repository.business.PaymentsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentsService {

    private final BorrowRepository borrowRepository;
    private final PaymentsRepository paymentsRepository;



    // Tüm ödünç alınan kitapları getir
    public List<BorrowResponse> getAllBorrows() {
        return borrowRepository.findAll().stream()
                .map(this::mapToBorrowResponse)
                .collect(Collectors.toList());
    }

    // Borrow nesnesini BorrowResponse'a dönüştür
    private BorrowResponse mapToBorrowResponse(Borrow borrow) {
        return BorrowResponse.builder()
                .id(borrow.getId())
                .bookId(borrow.getBookId())
                .bookTitle(borrow.getTitle())
                .bookAuthor(borrow.getAuthor())
                .startDate(borrow.getStartDate())
                .endDate(borrow.getEndDate())
                .build();
    }


    public Payments createPayments(PaymentsRequest paymentRequest) {
        Payments payment = new Payments();
        payment.setAmount(paymentRequest.getAmount());
        payment.setBorrowId(paymentRequest.getBorrowId());
        payment.setTitle(paymentRequest.getTitle());
        payment.setAuthor(paymentRequest.getAuthor());
        payment.setDaysLate(paymentRequest.getDaysLate());

        return paymentsRepository.save(payment);
    }



}
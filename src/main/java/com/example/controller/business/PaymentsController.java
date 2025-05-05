package com.example.controller.business;

import com.example.entity.concretes.business.Payments;
import com.example.payload.business.ResponseMessage;
import com.example.payload.request.business.BorrowHistoryRequest;
import com.example.payload.request.business.BorrowRequest;
import com.example.payload.request.business.PaymentsRequest;
import com.example.payload.response.business.BorrowHistoryResponse;
import com.example.payload.response.business.BorrowResponse;
import com.example.payload.response.business.PaymentsResponse;
import com.example.service.business.PaymentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentsController {


    private final PaymentsService paymentsService;

    @PostMapping("/create")
    public ResponseEntity<PaymentsResponse> createNewPayment(@RequestBody PaymentsRequest paymentRequest) {
        Payments createdPayment = paymentsService.createPayments(paymentRequest);

        PaymentsResponse response = new PaymentsResponse();
        response.setId(createdPayment.getId());
        response.setMessage("Yeni ödeme kaydı başarıyla oluşturuldu.");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BorrowResponse>> getAllBorrows() {
        return ResponseEntity.ok(paymentsService.getAllBorrows());
    }

    @GetMapping("/borrows-with-end-date")
    public ResponseEntity<List<BorrowResponse>> getBorrowsWithEndDate() {
        return ResponseEntity.ok(paymentsService.getAllBorrows());
    }

}

package com.example.controller.business;

import com.example.payload.business.ResponseMessage;
import com.example.payload.request.business.BorrowHistoryRequest;
import com.example.payload.request.business.BorrowRequest;
import com.example.payload.response.business.BorrowHistoryResponse;
import com.example.payload.response.business.BorrowResponse;
import com.example.payload.response.business.PaymentsResponse;
import com.example.service.business.PaymentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentsController {
    private final PaymentsService paymentsService;

    @PostMapping("/create")
    public ResponseEntity<PaymentsResponse> createPayments(@Valid @RequestBody BorrowRequest borrowRequest) {
        return ResponseEntity.ok(paymentsService.createPayments(borrowRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BorrowResponse>> getAllBorrows() {
        return ResponseEntity.ok(paymentsService.getAllBorrows());
    }

}

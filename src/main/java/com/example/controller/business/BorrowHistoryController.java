package com.example.controller.business;


import com.example.entity.concretes.business.BorrowHistory;
import com.example.payload.business.ResponseMessage;
import com.example.payload.request.business.BorrowHistoryRequest;
import com.example.payload.request.business.BorrowRequest;
import com.example.payload.response.business.BorrowHistoryResponse;
import com.example.payload.response.business.BorrowResponse;
import com.example.service.business.BorrowHistoryService;
import jakarta.validation.Valid;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowHistory")
@RequiredArgsConstructor
public class BorrowHistoryController {

    private final BorrowHistoryService borrowHistoryService;

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage<BorrowHistoryResponse>> createBorrowHistory(@Valid @RequestBody BorrowHistoryRequest borrowHistoryRequest) {
        return ResponseEntity.ok(borrowHistoryService.createBorrowHistory(borrowHistoryRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BorrowHistoryResponse>> getAllBorrows() {
        return ResponseEntity.ok(borrowHistoryService.getAllBorrows());
    }






}

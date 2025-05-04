package com.example.controller.business;

import com.example.payload.business.ResponseMessage;
import com.example.payload.business.ReturnBookRequest;
import com.example.payload.request.business.BorrowRequest;
import com.example.payload.response.business.BorrowResponse;
import com.example.service.business.BorrowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrows")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    // Create a new borrow
    @PostMapping("/create")
    public ResponseEntity<ResponseMessage<BorrowResponse>> createBorrow(@Valid @RequestBody BorrowRequest borrowRequest) {
        return ResponseEntity.ok(borrowService.createBorrow(borrowRequest));
    }

    // Controller'daki returnBook metodu
    @PutMapping("/return")
    public ResponseEntity<ResponseMessage<BorrowResponse>> returnBook(@Valid @RequestBody ReturnBookRequest returnBookRequest) {
        return ResponseEntity.ok(borrowService.returnBook(returnBookRequest));
    }

    // Get all borrows
    @GetMapping("/all")
    public ResponseEntity<List<BorrowResponse>> getAllBorrows() {
        return ResponseEntity.ok(borrowService.getAllBorrows());
    }

   /* // Get active borrows
    @GetMapping("/active")
    public ResponseEntity<List<BorrowResponse>> getActiveBorrows() {
        return ResponseEntity.ok(borrowService.getActiveBorrows());
    }

    // Get borrows by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BorrowResponse>> getBorrowsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(borrowService.getBorrowsByUserId(userId));
    }

    // Get active borrows by user id
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<BorrowResponse>> getActiveBorrowsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(borrowService.getActiveBorrowsByUserId(userId));
    }*/

    // Get borrow by id
    @GetMapping("/{id}")
    public ResponseEntity<BorrowResponse> getBorrowById(@PathVariable Long id) {
        return ResponseEntity.ok(borrowService.getBorrowById(id));
    }


}
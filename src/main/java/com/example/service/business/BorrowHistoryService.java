package com.example.service.business;

import com.example.entity.concretes.business.Book;
import com.example.entity.concretes.business.Borrow;
import com.example.entity.concretes.business.BorrowHistory;
import com.example.exception.ResourceNotFoundException;
import com.example.payload.business.ResponseMessage;
import com.example.payload.request.business.BorrowHistoryRequest;
import com.example.payload.response.business.BorrowHistoryResponse;
import com.example.payload.response.business.BorrowResponse;
import com.example.repository.business.BookRepository;
import com.example.repository.business.BorrowHistoryRepository;
import com.example.repository.business.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowHistoryService {
    private final BorrowHistoryRepository borrowHistoryRepository;
    private final BorrowRepository borrowRepository;

    private final int MAX_BOOKS_PER_USER = 3;

    public ResponseMessage<BorrowHistoryResponse> createBorrowHistory(BorrowHistoryRequest borrowHistoryRequest) {

        Borrow borrow = borrowRepository.findById(borrowHistoryRequest.getBorrowId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + borrowHistoryRequest.getBorrowId()));


        List<BorrowHistory> borrowHistories = borrowHistoryRepository.findByBorrowId(borrow.getId());


        BorrowHistory borrowHistory = BorrowHistory.builder()
                .borrowId(borrow.getId())
                .title(borrow.getTitle())
                .author(borrow.getAuthor())
                .category(borrow.getCategory())
                .receivedDate(borrowHistoryRequest.getReceivedDate() != null ? borrowHistoryRequest.getReceivedDate() : LocalDate.now())
                .returnedDate(borrowHistoryRequest.getReturnedDate() != null ? borrowHistoryRequest.getReturnedDate() : LocalDate.now().plusDays(15))
                .build();

        BorrowHistory savedBorrow = borrowHistoryRepository.save(borrowHistory);

        return ResponseMessage.<BorrowHistoryResponse>builder()
                .message("Book successfully borrowed")
                .httpStatus(HttpStatus.CREATED)
                .object(mapToBorrowHistoryResponse(savedBorrow))
                .build();
    }


    // Get all borrows
    public List<BorrowHistoryResponse> getAllBorrows() {
        return borrowHistoryRepository.findAll().stream()
                .map(this::mapToBorrowHistoryResponse)
                .collect(Collectors.toList());
    }

   /* public BorrowHistoryResponse getBorrowHistoryById(Long id) {
        BorrowHistory borrowHistory = borrowHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found with id: " + id));

        return mapToBorrowHistoryResponse(borrowHistory);
    }*/

    private BorrowHistoryResponse mapToBorrowHistoryResponse(BorrowHistory borrowHistory) {
        return BorrowHistoryResponse.builder()
                .id(borrowHistory.getId())
                .borrowId(borrowHistory.getBorrowId())
                .borrowTitle(borrowHistory.getTitle())
                .borrowAuthor(borrowHistory.getAuthor())
                .borrowCategory(borrowHistory.getCategory())
                .receivedDate(borrowHistory.getReceivedDate())
                .returnedDate(borrowHistory.getReturnedDate())
                .build();
    }
}

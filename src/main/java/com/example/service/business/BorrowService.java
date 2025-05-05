package com.example.service.business;

import com.example.entity.concretes.business.Book;
import com.example.entity.concretes.business.Borrow;
import com.example.entity.concretes.user.User;
import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.payload.business.ResponseMessage;
import com.example.payload.business.ReturnBookRequest;
import com.example.payload.request.business.BorrowRequest;
import com.example.payload.response.business.BorrowResponse;
import com.example.repository.business.BookRepository;
import com.example.repository.business.BorrowRepository;
import com.example.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;

    private final int MAX_BOOKS_PER_USER = 3;

    public ResponseMessage<BorrowResponse> createBorrow(BorrowRequest borrowRequest) {

        Book book = bookRepository.findById(borrowRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + borrowRequest.getBookId()));


        List<Borrow> bookBorrows = borrowRepository.findByBookId(book.getId());

        // Create borrow record
        Borrow borrow = Borrow.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .category(book.getCategory())
                .startDate(borrowRequest.getStartDate() != null ? borrowRequest.getStartDate() : LocalDate.now())
                .endDate(borrowRequest.getEndDate() != null ? borrowRequest.getEndDate() : LocalDate.now().plusDays(15))
                .build();

        Borrow savedBorrow = borrowRepository.save(borrow);

        return ResponseMessage.<BorrowResponse>builder()
                .message("Book successfully borrowed")
                .httpStatus(HttpStatus.CREATED)
                .object(mapToBorrowResponse(savedBorrow))
                .build();
    }

    // Return a borrowed book
    public ResponseMessage<BorrowResponse> returnBook(ReturnBookRequest returnBookRequest) {
        Borrow borrow = borrowRepository.findById(returnBookRequest.getBorrowId())
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found with id: " + returnBookRequest.getBorrowId()));

        // Kayıt siliniyor
        borrowRepository.delete(borrow);

        return ResponseMessage.<BorrowResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(mapToBorrowResponse(borrow))
                .build();
    }

    // Get all borrows
    public List<BorrowResponse> getAllBorrows() {
        return borrowRepository.findAll().stream()
                .map(this::mapToBorrowResponse)
                .collect(Collectors.toList());
    }


    public BorrowResponse getBorrowById(Long id) {
        Borrow borrow = borrowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found with id: " + id));

        return mapToBorrowResponse(borrow);
    }

    private BorrowResponse mapToBorrowResponse(Borrow borrow) {
        return BorrowResponse.builder()
                .bookTitle(borrow.getTitle())
                .bookAuthor(borrow.getAuthor())
                .endDate(borrow.getEndDate())
                .build();
    }
}
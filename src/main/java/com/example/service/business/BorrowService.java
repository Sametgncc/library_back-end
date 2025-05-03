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
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private final int MAX_BOOKS_PER_USER = 3;

    public ResponseMessage<BorrowResponse> createBorrow(BorrowRequest borrowRequest) {
        User user = userRepository.findById(borrowRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + borrowRequest.getUserId()));

        Book book = bookRepository.findById(borrowRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + borrowRequest.getBookId()));


        int activeBorrows = borrowRepository.countActiveBooksByUserId(user.getId());
        if (activeBorrows >= MAX_BOOKS_PER_USER) {
            throw new BadRequestException("User has already borrowed maximum number of books: " + MAX_BOOKS_PER_USER);
        }


        List<Borrow> bookBorrows = borrowRepository.findByBookId(book.getId());
        boolean isBookBorrowed = bookBorrows.stream()
                .anyMatch(borrow -> !borrow.isReturned());
        if (isBookBorrowed) {
            throw new BadRequestException("Book is already borrowed by another user");
        }

        // Create borrow record
        Borrow borrow = Borrow.builder()
                .user(user)
                .book(book)
                .borrowDate(borrowRequest.getBorrowDate() != null ? borrowRequest.getBorrowDate() : LocalDate.now())
                .returnDate(borrowRequest.getReturnDate() != null ? borrowRequest.getReturnDate() : LocalDate.now().plusDays(15))
                .isReturned(false)
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

        if (borrow.isReturned()) {
            throw new BadRequestException("Book is already returned");
        }

        borrow.setReturned(true);
        Borrow savedBorrow = borrowRepository.save(borrow);

        return ResponseMessage.<BorrowResponse>builder()
                .message("Book successfully returned")
                .httpStatus(HttpStatus.OK)
                .object(mapToBorrowResponse(savedBorrow))
                .build();
    }

    // Get all borrows
    public List<BorrowResponse> getAllBorrows() {
        return borrowRepository.findAll().stream()
                .map(this::mapToBorrowResponse)
                .collect(Collectors.toList());
    }

    // Get active borrows
    public List<BorrowResponse> getActiveBorrows() {
        return borrowRepository.findByIsReturnedFalse().stream()
                .map(this::mapToBorrowResponse)
                .collect(Collectors.toList());
    }

    // Get borrows by user id
    public List<BorrowResponse> getBorrowsByUserId(Long userId) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return borrowRepository.findByUserId(userId).stream()
                .map(this::mapToBorrowResponse)
                .collect(Collectors.toList());
    }

    // Get active borrows by user id
    public List<BorrowResponse> getActiveBorrowsByUserId(Long userId) {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return borrowRepository.findActiveBooksByUserId(userId).stream()
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
                .id(borrow.getId())
                .userId(borrow.getUser().getId())
                .bookId(borrow.getBook().getId())
                .bookAuthor(borrow.getBook().getAuthor())
                .borrowDate(borrow.getBorrowDate())
                .returnDate(borrow.getReturnDate())
                .isReturned(borrow.isReturned())
                .build();
    }
}
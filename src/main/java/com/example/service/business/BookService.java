package com.example.service.business;

import com.example.entity.concretes.business.Book;
import com.example.payload.business.ResponseMessage;
import com.example.payload.mappers.BookMapper;
import com.example.payload.request.business.BookRequest;
import com.example.payload.response.business.BookResponse;
import com.example.repository.business.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;




    public ResponseMessage<BookResponse> addBook(@Valid BookRequest bookRequest) {
        Book book = BookMapper.mapToEntity(bookRequest);
        Book savedBook = bookRepository.save(book);
        BookResponse response = BookMapper.mapToResponse(savedBook);
        return new ResponseMessage<>(response, "Book added successfully", HttpStatus.CREATED);

    }

    // kitaplari getirmek için
    public List<BookResponse> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream()
                .map(BookMapper::mapToResponse)
                .collect(Collectors.toList());
    }


    public BookResponse getBookById(Long id) {

    }

    public ResponseMessage<BookResponse> updateBook(Long id, @Valid BookRequest bookRequest) {

    }

    public void deleteBook(Long id) {

    }
}

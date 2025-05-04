package com.example.controller.business;

import com.example.entity.concretes.business.Book;
import com.example.payload.business.ResponseMessage;
import com.example.payload.request.business.BookRequest;
import com.example.payload.response.business.BookResponse;
import com.example.repository.business.BookRepository;
import com.example.service.business.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;



        // Kitapları listele
        @GetMapping
        public ResponseEntity<List<BookResponse>> getAllBooks() {
            return ResponseEntity.ok(bookService.getAllBooks());
        }

        // Belirli bir kitabı getir
        @GetMapping("/{id}")
        public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
            return ResponseEntity.ok(bookService.getBookById(id));
        }

        // Kitap ekleme
        @PostMapping("/save") // http://localhost:8081/book/saveBook
        public ResponseMessage<BookResponse> addBook(@RequestBody @Valid BookRequest bookRequest) {
            return bookService.addBook(bookRequest);
        }

    @PostMapping("/saveAll")
    public ResponseEntity<ResponseMessage<String>> saveAllBooks(@RequestBody List<BookRequest> bookRequests) {
        if (bookRequests == null || bookRequests.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    ResponseMessage.<String>builder()
                            .message("Kitap listesi boş olamaz.")
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }

        List<Book> books = bookRequests.stream()
                .map(req -> Book.builder()
                        .title(req.getTitle())
                        .author(req.getAuthor())
                        .isbn(req.getIsbn())
                        .category(req.getCategory())
                        .build())
                .toList();

        bookRepository.saveAll(books);

        return ResponseEntity.ok(
                ResponseMessage.<String>builder()
                        .message("Tüm kitaplar başarıyla kaydedildi.")
                        .httpStatus(HttpStatus.OK)
                        .build()
        );
    }




    // Kitap güncelleme
        @PutMapping("/updateBook/{id}")
        public ResponseMessage<BookResponse> updateBook(@PathVariable Long id, @RequestBody @Valid BookRequest bookRequest) {
            return bookService.updateBook(id, bookRequest);
        }

}



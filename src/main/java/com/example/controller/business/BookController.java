package com.example.controller.business;

import com.example.payload.business.ResponseMessage;
import com.example.payload.request.business.BookRequest;
import com.example.payload.response.business.BookResponse;
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



        // Kitapları listele
        @GetMapping("/")
        public ResponseEntity<List<BookResponse>> getAllBooks() {
            return ResponseEntity.ok(bookService.getAllBooks());
        }

        // Belirli bir kitabı getir
        @GetMapping("/{id}")
        public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
            return ResponseEntity.ok(bookService.getBookById(id));
        }

        // Kitap ekleme
        @PostMapping("/saveBook")
        public ResponseMessage<BookResponse> addBook(@RequestBody @Valid BookRequest bookRequest) {
            return bookService.addBook(bookRequest);
        }

        // Kitap güncelleme
        @PutMapping("/updateBook/{id}")
        public ResponseMessage<BookResponse> updateBook(@PathVariable Long id, @RequestBody @Valid BookRequest bookRequest) {
            return bookService.updateBook(id, bookRequest);
        }

        // Kitap silme
        @DeleteMapping("/deleteBook/{id}")
        public ResponseMessage<Integer> deleteBook(@PathVariable Long id) {
            bookService.deleteBook(id);
            return new ResponseMessage<>(HttpStatus.NO_CONTENT.value(), "kitap silindi", HttpStatus.OK);
        }
}



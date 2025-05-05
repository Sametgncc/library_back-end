package com.example.controller.business;

import com.example.entity.concretes.business.Book;
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


        // tum kitapları listele
        @GetMapping
        public ResponseEntity<List<BookResponse>> getAllBooks() {
            return ResponseEntity.ok(bookService.getAllBooks());
        }

        // kitapları belirli id ile listele
        @GetMapping("/{id}")
        public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
            return ResponseEntity.ok(bookService.getBookById(id));
        }

        @PostMapping("/saveBook") // http://localhost:8081/book/saveBook
        public ResponseMessage<BookResponse> addBook(@RequestBody @Valid BookRequest bookRequest) {
            return bookService.addBook(bookRequest);
        }

        @PutMapping("/updateBook/{id}")
        public ResponseMessage<BookResponse> updateBook(@PathVariable Long id, @RequestBody @Valid BookRequest bookRequest) {
            return bookService.updateBook(id, bookRequest);
        }

        @DeleteMapping("/deleteBook/{id}")
        public ResponseMessage<Integer> deleteBook(@PathVariable Long id) {
            bookService.deleteBook(id);
            return new ResponseMessage<>(HttpStatus.NO_CONTENT.value(), "kitap silindi", HttpStatus.OK);
        }

        @PostMapping("/{userId}/favorite/{bookId}")
        public ResponseEntity<?> addToFavorites(@PathVariable Long userId, @PathVariable Long bookId) {
            bookService.addBookToFavorites(userId, bookId);
            return ResponseEntity.ok("Favorilere eklendi");
        }

        @GetMapping("/{userId}/favorites")
        public ResponseEntity<List<Book>> getFavorites(@PathVariable Long userId) {
            return ResponseEntity.ok(bookService.getFavoritesByUser(userId));
        }

        @DeleteMapping("/{userId}/favorite/{bookId}")
        public ResponseEntity<?> removeFromFavorites(@PathVariable Long userId, @PathVariable Long bookId) {
            bookService.removeBookFromFavorites(userId, bookId);
            return ResponseEntity.ok("Favorilerden çıkarıldı");
        }


}



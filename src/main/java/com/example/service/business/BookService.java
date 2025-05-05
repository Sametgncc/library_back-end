package com.example.service.business;

import com.example.entity.concretes.business.Book;
import com.example.entity.concretes.business.FavoriteBook;
import com.example.exception.ResourceNotFoundException;
import com.example.payload.business.ResponseMessage;
import com.example.payload.mappers.BookMapper;
import com.example.payload.request.business.BookRequest;
import com.example.payload.response.business.BookResponse;
import com.example.repository.business.BookRepository;
import com.example.repository.business.FavoriteBookRepository;
import com.example.repository.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final FavoriteBookRepository favoriteBookRepository;

    public ResponseMessage<BookResponse> addBook(BookRequest bookRequest) {

        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .category(bookRequest.getCategory())
                .status("Mevcut")
                .build();


        Book savedBook = bookRepository.save(book);


        BookResponse bookResponse = BookResponse.builder()
                .id(savedBook.getId())
                .title(savedBook.getTitle())
                .author(savedBook.getAuthor())
                .category(savedBook.getCategory())
                .build();

        return ResponseMessage.<BookResponse>builder()
                .message("Kitap başarıyla eklendi.")
                .httpStatus(HttpStatus.CREATED)
                .object(bookResponse)
                .build();
    }




    public List<BookResponse> getAllBooks() {

        List<Book> bookList = bookRepository.findAll();


        return bookList.stream()
                .map(BookMapper::mapToResponse)
                .collect(Collectors.toList());
    }


    public BookResponse getBookById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return BookMapper.mapToResponse(book);
    }


    public ResponseMessage<BookResponse> updateBook(Long id, @Valid BookRequest bookRequest) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));


        existingBook.setTitle(bookRequest.getTitle());
        existingBook.setAuthor(bookRequest.getAuthor());

        Book updatedBook = bookRepository.save(existingBook);


        BookResponse updatedBookResponse = BookMapper.mapToResponse(updatedBook);

        return ResponseMessage.<BookResponse>builder()
                .object(updatedBookResponse)
                .message("Book updated successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public void deleteBook(Long id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));


        bookRepository.delete(existingBook);
    }

    // favri kismi icin servis method
    public void addBookToFavorites(Long userId, Long bookId) {
        Optional<FavoriteBook> existing = favoriteBookRepository.findByUserIdAndBookId(userId, bookId);
        if (existing.isPresent()) throw new RuntimeException("Zaten ekli");

        FavoriteBook favorite = new FavoriteBook();
        favorite.setUser(userRepository.findById(userId).orElseThrow());
        favorite.setBook(bookRepository.findById(bookId).orElseThrow());
        favoriteBookRepository.save(favorite);
    }

    public List<Book> getFavoritesByUser(Long userId) {
        return favoriteBookRepository.findByUserId(userId)
                .stream()
                .map(FavoriteBook::getBook)
                .collect(Collectors.toList());
    }

    public void removeBookFromFavorites(Long userId, Long bookId) {
        FavoriteBook favorite = favoriteBookRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new RuntimeException("Favori bulunamadı"));

        favoriteBookRepository.delete(favorite);
    }


}

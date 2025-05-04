package com.example.service.business;

import com.example.entity.concretes.business.Book;
//import com.example.entity.concretes.business.Categories;
//import com.example.entity.concretes.business.Categories;
import com.example.exception.ResourceNotFoundException;
import com.example.payload.business.ResponseMessage;
import com.example.payload.mappers.BookMapper;
import com.example.payload.request.business.BookRequest;
import com.example.payload.response.business.BookResponse;
import com.example.repository.business.BookRepository;
//import com.example.repository.business.CategoryRepository;
//import com.example.repository.business.CategoryRepository;
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
    //private final CategoryRepository categoryRepository;

    // BookMapper'ı static metodlar ile çağıracağımız için burada inject etmeye gerek yok.
    // private final BookMapper bookMapper; // Bu satırı kaldırabilirsiniz

    // Kitap eklemek için
    public ResponseMessage<BookResponse> addBook(BookRequest bookRequest) {
        /*// 1. Kategori kontrolü
        Categories category = categoryRepository.findById(bookRequest.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Kategori bulunamadı: " + bookRequest.getCategory()));*/

        // 2. Book nesnesini oluştur
        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .category(bookRequest.getCategory())
                .status("Mevcut")
                .build();

        // 3. Kitabı kaydet
        Book savedBook = bookRepository.save(book);

        // 4. Response objesini oluştur
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



    // Tüm kitapları getirmek için
    public List<BookResponse> getAllBooks() {
        // Kitapları veritabanından çek
        List<Book> bookList = bookRepository.findAll();

        // Kitapları response formatında dönüştür
        return bookList.stream()
                .map(BookMapper::mapToResponse)  // BookMapper'ı doğrudan kullanıyoruz
                .collect(Collectors.toList());
    }

    // ID ile kitap getirmek için
    public BookResponse getBookById(Long id) {
        // Kitap bulunmazsa hata fırlatılabilir
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return BookMapper.mapToResponse(book); // Doğrudan BookMapper'ı kullan
    }

    // Kitap güncellemek için
    public ResponseMessage<BookResponse> updateBook(Long id, @Valid BookRequest bookRequest) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Mevcut kitabı güncelle
        existingBook.setTitle(bookRequest.getTitle());
        existingBook.setAuthor(bookRequest.getAuthor());
        //existingBook.setCategory(bookRequest.getCategory());

        // Güncellenen kitabı kaydet
        Book updatedBook = bookRepository.save(existingBook);

        // Güncellenen kitabı Response'e dönüştür
        BookResponse updatedBookResponse = BookMapper.mapToResponse(updatedBook);

        // ResponseMessage dön
        return ResponseMessage.<BookResponse>builder()
                .object(updatedBookResponse)
                .message("Book updated successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    // Kitap silmek için
    public void deleteBook(Long id) {
        // Kitap bulunmazsa hata fırlat
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Kitap sil
        bookRepository.delete(existingBook);
    }
}

package com.example.payload.mappers;

import com.example.entity.concretes.business.Book;
import com.example.payload.request.business.BookRequest;
import com.example.payload.response.business.BookResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class BookMapper {

    public static Book mapToEntity(BookRequest request) {
        return Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .publisher(request.getPublisher())
                .publishDate(request.getPublishDate())
                .quantity(request.getQuantity())
                .category(category)
                .build();
    }

    public static BookResponse mapToResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publishDate(book.getPublishDate())
                .quantity(book.getQuantity())
                .categoryName(book.getCategory() != null ? book.getCategory().getName() : null)
                .build();
    }


    public BookResponse mapBookToResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publishDate(book.getPublishDate())
                .quantity(book.getQuantity())
                .categoryName(book.getCategory() != null ? book.getCategory().getName() : null)
                .build();
    }
}
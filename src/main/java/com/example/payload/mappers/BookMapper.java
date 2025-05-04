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
                .category(request.getCategory())
                .status("Mevcut")
                .build();
    }


    public static BookResponse mapToResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .category(book.getCategory())
                //.category(book.getCategory() != null ? book.getCategory().getName() : null)
                .build();
    }


}
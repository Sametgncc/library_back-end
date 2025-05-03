package com.example.payload.request.business;

import com.example.entity.concretes.business.Categories;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class BookRequest {

    @NotBlank(message = "Kitap adı boş olamaz")
    private String title;

    @NotBlank(message = "Yazar adı boş olamaz")
    private String author;

    private Long categoryId;

    private String publicationYear;

    @NotBlank(message = "ISBN boş olamaz")
    private String isbn;

    @NotNull
    private Categories category;



}

package com.example.payload.request.business;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

    @NotBlank(message = "Kitap adı boş olamaz")
    private String title;

    @NotBlank(message = "Yazar adı boş olamaz")
    private String author;


    private Integer publicationYear;

    @NotBlank(message = "ISBN boş olamaz")
    private String isbn;

    @NotBlank(message = "Kategori boş olamaz")
    private String category;



}

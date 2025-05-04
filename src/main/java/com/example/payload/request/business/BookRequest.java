package com.example.payload.request.business;

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

    @NotBlank(message = "ISBN boş olamaz")
    private String isbn;

    private String category;


}

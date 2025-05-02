package com.example.payload.response.business;



import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publishDate;
    private Integer quantity;
    private String categoryName; // sadece isim gösterelim
}

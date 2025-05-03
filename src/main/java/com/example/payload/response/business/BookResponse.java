package com.example.payload.response.business;



import com.example.entity.concretes.business.Categories;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private Integer quantity;
    private String category;
}

package com.example.payload.request.business;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class PaymentsRequest {
    @NotBlank(message = "Book Id boş olamaz")
    private Long borrowId;

    @NotBlank(message = "Kitap adı boş olamaz")
    private String title;

    @NotBlank(message = "Yazar adı boş olamaz")
    private String author;

    @NotNull(message = "Geciken gün sayısı boş olamaz.")
    private Long daysLate;

    @NotNull(message = " Borç boş olamaz.")
    private Long amount;
}

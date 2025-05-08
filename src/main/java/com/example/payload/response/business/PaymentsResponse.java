package com.example.payload.response.business;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentsResponse {
    private Long id;
    private String title;
    private String author;
    private Long daysLate;
    private Long debtAmount;
}

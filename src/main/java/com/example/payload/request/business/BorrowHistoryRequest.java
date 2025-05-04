package com.example.payload.request.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowHistoryRequest {

    @NotNull(message =  "Borrow Id cannot be empty")
    private Long borrowId;
    @NotNull(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "Author cannot be empty")
    private String author;

    @NotNull(message = "Category cannot be empty")
    private String category;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Istanbul")
    private LocalDate receivedDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Istanbul")
    private LocalDate returnedDate;
}

package com.example.payload.business;


import com.example.payload.response.business.BookResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL) // Null veriyi taşımaz daha temiz bir çıktı sağlar bizim için
public class ResponseMessage <E>{

    private E object;
    private String message;
    private HttpStatus httpStatus;


}

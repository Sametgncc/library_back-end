package com.example.payload.request.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequest {

    @NotNull(message = "Email must be not empty")
    private String email;
    @NotNull(message = "Password must be not empty")
    private String password;

}

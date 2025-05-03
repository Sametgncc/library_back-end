package com.example.payload.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserRequest {

    @NotNull(message = "Username must be not empty")
    private String username;

    @NotNull(message = "Email must be not empty")
    @Email
    private String email;

    @NotNull(message = "Password must be not empty")
    private String password;




}

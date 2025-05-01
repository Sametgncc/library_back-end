package com.example.payload.request.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserRequest {

    private String username;
    private String email;
    private String password;




}

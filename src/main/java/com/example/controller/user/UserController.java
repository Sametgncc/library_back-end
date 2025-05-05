package com.example.controller.user;


import com.example.payload.business.ResponseMessage;
import com.example.payload.request.authentication.LoginRequest;
import com.example.payload.request.user.UserRequest;
import com.example.payload.request.user.UserRequestWithoutPassword;
import com.example.payload.response.abstracts.BaseUserResponse;
import com.example.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @PostMapping("/register")
    public ResponseMessage<String> registerUser(@RequestBody UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        userService.login(loginRequest);
        return ResponseEntity.ok("Giriş başarılı");
    }




}

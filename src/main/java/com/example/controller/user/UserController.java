package com.example.controller.user;


import com.example.payload.business.ResponseMessage;
import com.example.payload.request.user.UserRequest;
import com.example.payload.response.abstracts.BaseUserResponse;
import com.example.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest) {
        userService.saveUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }

    //getUserById() *********************************************************
    @GetMapping("getUserById/{userId}")  // http://localhost:8080/user/getUserById/1
    public ResponseMessage<BaseUserResponse> getUserById(@PathVariable Long userId){
        return userService.getUserById(userId) ;
    }

    //getAllUsers() *********************************************************




}

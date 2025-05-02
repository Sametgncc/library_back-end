package com.example.controller.user;


import com.example.payload.business.ResponseMessage;
import com.example.payload.request.user.UserRequest;
import com.example.payload.request.user.UserRequestWithoutPassword;
import com.example.payload.response.abstracts.BaseUserResponse;
import com.example.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    //updateUser() *********************************************************
    @PatchMapping("/updateUser")  // http://localhost:8080/user/updateUser
    public ResponseEntity<String>updateUser(@RequestBody @Valid
                                                UserRequestWithoutPassword userRequestWithoutPassword,
                                            HttpServletRequest request){
        return userService.updateUserForUsers(userRequestWithoutPassword, request) ;
    }

    @PutMapping("/update/{userId}") // http://localhost:8080/user/update/1
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    //!!! donen deger BaseUserResponse --> polymorphism
    public ResponseMessage<BaseUserResponse> updateAdminDeanViceDeanForAdmin(
            @RequestBody @Valid UserRequest userRequest,
            @PathVariable Long userId){
        return userService.updateUser(userRequest,userId) ;
    }

    //deleteUser() *********************************************************
    @DeleteMapping("/delete/{id}") //http://localhost:8081/user/delete/3
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id ,
                                                 HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(userService.deleteUserById(id , httpServletRequest)) ;
    }





}

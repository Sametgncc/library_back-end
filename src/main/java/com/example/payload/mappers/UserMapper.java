package com.example.payload.mappers;

import com.example.entity.concretes.user.User;
import com.example.payload.request.abstracts.BaseUserRequest;
import com.example.payload.request.user.UserRequest;
import com.example.payload.response.abstracts.BaseUserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapUserRequestToUpdatedUser(UserRequest userRequest, Long userId){
        return User.builder()
                .id(userId)
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .build() ;
    }

    public BaseUserResponse mapUserToUserResponse(User savedUser) {
        return BaseUserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .build();
    }
}

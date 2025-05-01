package com.example.service.user;

import com.example.entity.concretes.user.User;
import com.example.exception.ResourceNotFoundException;
import com.example.payload.business.ResponseMessage;
import com.example.payload.request.user.UserRequest;
import com.example.payload.response.abstracts.BaseUserResponse;
import com.example.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void saveUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user);
    }


    public ResponseMessage<BaseUserResponse> getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        BaseUserResponse response = new BaseUserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());

        return ResponseMessage.<BaseUserResponse>builder()
                .message("User found successfully.")
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();
    }

}

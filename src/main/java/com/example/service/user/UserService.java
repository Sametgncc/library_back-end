package com.example.service.user;

import com.example.entity.concretes.user.User;
import com.example.entity.enums.RoleType;
import com.example.exception.BadRequestException;
import com.example.exception.ConflictException;
import com.example.exception.ResourceNotFoundException;
import com.example.payload.business.ResponseMessage;
import com.example.payload.mappers.UserMapper;
import com.example.payload.request.authentication.LoginRequest;
import com.example.payload.request.user.UserRequest;
import com.example.payload.request.user.UserRequestWithoutPassword;
import com.example.payload.response.abstracts.BaseUserResponse;
import com.example.payload.response.user.AuthResponse;
import com.example.repository.user.UserRepository;
import com.example.service.helper.MethodHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MethodHelper methodHelper;
    private final UserMapper userMapper;


    public ResponseMessage<String> saveUser(UserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ConflictException("Bu email zaten kullanılıyor.");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user);

        return ResponseMessage.<String>builder()
                .message("User registered successfully.")
                .httpStatus(HttpStatus.CREATED)
                .build();
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

    public ResponseEntity<String> updateUserForUsers(@Valid @RequestBody UserRequestWithoutPassword userRequestWithoutPassword, HttpServletRequest request) {

        String username = (String) request.getAttribute("username");

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kullanıcı bulunamadı");
        }

        // Güncellenmiş bilgileri kontrol et ve null degerler için hata mesajı döndür
        if (userRequestWithoutPassword.getUsername() == null || userRequestWithoutPassword.getEmail() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username veya email boş olamaz");
        }

        // Kullanıcı bilgilerini güncelleme
        user.setUsername(userRequestWithoutPassword.getUsername());
        user.setEmail(userRequestWithoutPassword.getEmail());


        userRepository.saveAndFlush(user);

        // okey mesaji
        return ResponseEntity.ok("Kullanıcı güncellendi");
    }


    public String deleteUserById(Long id, HttpServletRequest request) {
        // Silinecek user var mı kontrolü
        User userToDelete = methodHelper.isUserExist(id); // Silinmesi istenen user

        // Metodu tetikleyen user bilgisi alınıyor
        String username = (String) request.getAttribute("username");
        User currentUser = userRepository.findByUsername(username); // Silme işlemini talep eden user

        // Admin kullanıcıları her kullanıcıyı silebilir
       /* if (currentUser.getUserRole().getRoleType() == RoleType.ADMIN) {
            userRepository.deleteById(id);
            return "Kullanıcı başarıyla silindi.";
        }*/

        // Kullanıcı sadece kendi hesabını silebilir
        if (currentUser.getUsername().equals(userToDelete.getUsername())) {
            userRepository.deleteById(id);
            return "Kullanıcı başarıyla silindi.";
        }

        // Admin ve kullanıcı dışındaki yetkilere erişim engellenir
        throw new BadRequestException("Bu işlem için yetkiniz yok.");
    }



    public ResponseMessage<BaseUserResponse> updateUser(UserRequest userRequest, Long userId) {

        // Kullanıcı var mı kontrolü
        User user = methodHelper.isUserExist(userId);

        // DTO'dan POJO'ya dönüşüm işlemi
        User updatedUser = userMapper.mapUserRequestToUpdatedUser(userRequest, userId);

        // Şifreyi encode et
        if (userRequest.getPassword() != null) {
            updatedUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        // Kullanıcı rolünü koru (eski rol korunuyor)
       // updatedUser.setUserRole(user.getUserRole());

        // Güncellenmiş kullanıcıyı veritabanına kaydet
        User savedUser = userRepository.save(updatedUser);

        // Başarı mesajı ve güncellenmiş kullanıcı bilgisi ile yanıt oluştur
        return ResponseMessage.<BaseUserResponse>builder()
                .message("User updated successfully.")
                .httpStatus(HttpStatus.OK)
                .object(userMapper.mapUserToUserResponse(savedUser))
                .build();
    }


    public ResponseMessage<AuthResponse> login(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + loginRequest.getEmail()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid password");
        }
        return ResponseMessage.<AuthResponse>builder()
                .message("Login successful")
                .httpStatus(HttpStatus.OK)
                .build();


    }


}

package com.example.repository.user;

import com.example.entity.concretes.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByUsernameEquals(String username);

    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(@NotNull(message = "Email must be not empty") @Email String email);

    boolean existsByUsername(@NotNull(message = "Username must be not empty") String username);


}

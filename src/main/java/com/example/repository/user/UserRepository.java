package com.example.repository.user;

import com.example.entity.concretes.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByUsernameEquals(String username)
            ;
    // Özel sorgular veya metotlar ekleyebilirsin
}

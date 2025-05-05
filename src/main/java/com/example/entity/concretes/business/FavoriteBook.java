package com.example.entity.concretes.business;

import com.example.entity.concretes.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "favorite_books", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "book_id"}))

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private LocalDateTime createdAt = LocalDateTime.now();


}

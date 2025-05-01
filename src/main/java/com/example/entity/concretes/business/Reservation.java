package com.example.entity.concretes.business;

import com.example.entity.concretes.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "t_reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Rezervasyonu yapan kullanıcı
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Ayırtılan kitap
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDate reservationDate;

    private boolean active;
}

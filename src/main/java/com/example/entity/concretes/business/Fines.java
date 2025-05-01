package com.example.entity.concretes.business;

import com.example.entity.concretes.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "t_fines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Fines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ceza alan kullanıcı
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // İlgili borç (borç gecikmesi vb.)
    @OneToOne
    @JoinColumn(name = "borrow_id")
    private Borrow borrow;

    private BigDecimal amount;

    private boolean paid;

    private LocalDate issueDate;
}

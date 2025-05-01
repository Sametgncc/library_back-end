package com.example.entity.concretes.business;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "t_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Alt kategoriler
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private Set<Categories> subCategories;

    // Üst kategori
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Categories parentCategory;
}

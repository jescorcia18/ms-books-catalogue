package com.unir.ms_books_catalogue.data.model;


import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Data
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private LocalDate publicationDate;

    private String category;

    @Column(unique = true)
    private String isbn;

    // 1 a 5
    private Integer rating;

    // true = visible en el front, false = oculto
    private Boolean visible;
}

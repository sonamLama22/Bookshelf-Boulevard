package com.sonam.ecommerce.ecommercebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @NotBlank(message = "Book title must contain at least 1 character")
    @Size(max = 100, message = "Book title length must not exceed 100 characters")
    private String title;

    @NotBlank(message = "Author name must contain at least 1 character")
    @Size(max = 100, message = "Author name length must not exceed 100 characters")
    private String author;

    @Column(name = "price")
    private Integer price;

    //@NotBlank(message = "A book must have a genre")
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @NotBlank(message = "Book description must contain at least 1 character")
    private String description;

    // For Image
    private String fileName;
    private String fileType;

    @NotNull(message = "Copies must not be null")
    @Min(value = 0, message = "Copies available must be a positive number or 0")
    @Column(name = "copies_available")
    private int copiesAvailable;

    @Lob
    private byte[] data;


}

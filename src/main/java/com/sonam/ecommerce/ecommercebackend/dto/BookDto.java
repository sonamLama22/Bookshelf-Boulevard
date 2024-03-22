package com.sonam.ecommerce.ecommercebackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sonam.ecommerce.ecommercebackend.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

    private int bookId;
    private String title;
    private String author;
    private BigDecimal price;
    private int copiesAvailable;
    private Genre genre;
    private String description;
    private String fileName;
    private String fileType;

}

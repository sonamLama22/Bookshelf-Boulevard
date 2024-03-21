package com.sonam.ecommerce.ecommercebackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;
    private String product_name;
    private int price;

    // Many products can belong to a single category.
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String description;

    // For Image
    private String fileName;
    private String fileType;

    @Lob
    private byte[] data;

}

package com.sonam.ecommerce.ecommercebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddToCartRequest {
    private int bookId;
    private int quantity;
    private Integer price;
}

package com.sonam.ecommerce.ecommercebackend.dto;

import com.sonam.ecommerce.ecommercebackend.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private int id;
    private List<CartItem> cartItems;
    private int totalItem;
    private double totalPrice;

}

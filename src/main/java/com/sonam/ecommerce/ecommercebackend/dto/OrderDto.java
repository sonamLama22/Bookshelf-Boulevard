package com.sonam.ecommerce.ecommercebackend.dto;

import com.sonam.ecommerce.ecommercebackend.entity.OrderItem;
import com.sonam.ecommerce.ecommercebackend.entity.OrderStatus;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private int id;
 //   private int userId;
//    private List<OrderItem> orderItems;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private double totalPrice;
    private int totalItem;
    private OrderStatus orderStatus;
}

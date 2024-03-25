package com.sonam.ecommerce.ecommercebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sonam.ecommerce.ecommercebackend.entity.CartItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "cart_items")
    private List<CartItem> cartItems = new ArrayList<>();

   // FK "user_id"
   @JsonIgnore
   @OneToOne
   @JoinColumn(name = "user_id", referencedColumnName = "user_id")
   private User user;

   @Column(name = "total_item")
   private int totalItem;

   @Column(name = "total_price")
   private double totalPrice;


}

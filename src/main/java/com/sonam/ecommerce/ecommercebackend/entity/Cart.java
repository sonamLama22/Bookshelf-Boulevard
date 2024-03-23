package com.sonam.ecommerce.ecommercebackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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

    // a cart contains multiple books, a book can belong to multiple carts.
   @ManyToMany
   @JoinTable(
           name = "cart_book",
           joinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "book_id")
   )
   private List<Book> books;

   // FK "user_id"
   @OneToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "user_id", referencedColumnName = "user_id")
   private User user;

//    private int quantity;
//    private float amount;

}

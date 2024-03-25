package com.sonam.ecommerce.ecommercebackend.repository;

import com.sonam.ecommerce.ecommercebackend.entity.Book;
import com.sonam.ecommerce.ecommercebackend.entity.Cart;
import com.sonam.ecommerce.ecommercebackend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Integer> {
    @Query("select ci from CartItem ci where ci.cart=:cart and ci.book=:book and ci.userId=:userId")
    public CartItem cartItemExists(@Param("cart") Cart cart, @Param("book") Book book, @Param("userId")int userId);
    List<CartItem> findByCart(Cart cart);
}

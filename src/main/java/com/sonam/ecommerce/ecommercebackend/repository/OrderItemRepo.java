package com.sonam.ecommerce.ecommercebackend.repository;

import com.sonam.ecommerce.ecommercebackend.entity.Book;
import com.sonam.ecommerce.ecommercebackend.entity.CartItem;
import com.sonam.ecommerce.ecommercebackend.entity.Order;
import com.sonam.ecommerce.ecommercebackend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Integer> {

    @Query("SELECT oi FROM OrderItem oi WHERE oi.order = :order AND oi.book = :book  AND oi.order.user.userId = :userId")
    public CartItem isOrderItemExist(@Param("order") Order order, @Param("book") Book book, @Param("userId") int userId);

    //Optional<OrderItem> findByOrderAndProductAndSizeAndUserId(Order order,Book book,int userId);

}

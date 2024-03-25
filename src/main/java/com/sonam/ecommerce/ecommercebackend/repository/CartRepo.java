package com.sonam.ecommerce.ecommercebackend.repository;

import com.sonam.ecommerce.ecommercebackend.entity.Cart;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {

 public Cart findByUserUserId(int userId);

}

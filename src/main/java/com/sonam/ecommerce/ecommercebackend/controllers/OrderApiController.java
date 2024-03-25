package com.sonam.ecommerce.ecommercebackend.controllers;

import com.sonam.ecommerce.ecommercebackend.entity.Order;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.service.OrderService;
import com.sonam.ecommerce.ecommercebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderApiController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;

    @PostMapping("/user/create-order/{userId}")
    public ResponseEntity<Order> createOrder (@PathVariable int userId) throws ResourceNotFoundException {
        User user = userService.findUser(userId);
        Order order = orderService.createOrder(user);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}

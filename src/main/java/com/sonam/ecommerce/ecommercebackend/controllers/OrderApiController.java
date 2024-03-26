package com.sonam.ecommerce.ecommercebackend.controllers;

import com.sonam.ecommerce.ecommercebackend.dto.OrderDto;
import com.sonam.ecommerce.ecommercebackend.dto.PaymentDto;
import com.sonam.ecommerce.ecommercebackend.entity.Order;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.service.OrderService;
import com.sonam.ecommerce.ecommercebackend.service.PaymentService;
import com.sonam.ecommerce.ecommercebackend.service.UserService;
import com.sonam.ecommerce.ecommercebackend.service.implementation.PaymentServiceImpl;
import com.stripe.exception.StripeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderApiController {

    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    PaymentService paymentService;

    @PostMapping("/user/create-order/{userId}")
    @PreAuthorize("#userId == authentication.principal.userId")
    public ResponseEntity<PaymentDto> createOrder (@PathVariable int userId) throws ResourceNotFoundException, StripeException {
        User user = userService.findUser(userId);
        Order order = orderService.createOrder(user);
        PaymentDto paymentDto = paymentService.addPaymentLink(order);
        return new ResponseEntity<>(paymentDto, HttpStatus.CREATED);
    }

    @GetMapping("/admin/order/getAllOrders")
    public ResponseEntity<List<Order>> getAllProductHandler() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @PutMapping("/admin/order/{orderId}/confirmed")
    public ResponseEntity<Order> confirmedOrderHandler(@PathVariable int orderId) throws ResourceNotFoundException {
        Order order = orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/admin/order/{orderId}/ship")
    public ResponseEntity<Order> shippedOrderHandler(@PathVariable int orderId) throws ResourceNotFoundException {
        Order order = orderService.shippedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<Order> deliverOrderHandler(@PathVariable int orderId) throws ResourceNotFoundException {
        Order order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/admin/order/{orderId}cancel")
    public ResponseEntity<Order> cancelOrderHandler(@PathVariable int orderId) throws ResourceNotFoundException {
        Order order = orderService.canceledOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/admin/order/{orderId}delete")
    public ResponseEntity<?> deleteOrderHandler(@PathVariable int orderId) throws ResourceNotFoundException {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>("Order has been deleted", HttpStatus.OK);
    }
}

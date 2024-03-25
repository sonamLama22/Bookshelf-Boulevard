package com.sonam.ecommerce.ecommercebackend.controllers;

import com.sonam.ecommerce.ecommercebackend.dto.OrderDto;
import com.sonam.ecommerce.ecommercebackend.entity.Order;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.service.OrderService;
import com.sonam.ecommerce.ecommercebackend.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderApiController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;

    @PostMapping("/user/create-order/{userId}")
    public ResponseEntity<OrderDto> createOrder (@PathVariable int userId) throws ResourceNotFoundException {
        User user = userService.findUser(userId);
        Order order = orderService.createOrder(user);
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(order, orderDto);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
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

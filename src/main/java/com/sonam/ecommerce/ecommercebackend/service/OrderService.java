package com.sonam.ecommerce.ecommercebackend.service;

import com.sonam.ecommerce.ecommercebackend.entity.Order;
import com.sonam.ecommerce.ecommercebackend.entity.OrderStatus;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;

import java.util.List;

public interface OrderService {

    public Order createOrder(User user);
    public Order confirmedOrder(int orderId) throws ResourceNotFoundException;

    public Order findOrderById(int orderId) throws ResourceNotFoundException;
    public void deleteOrder(int orderId) throws ResourceNotFoundException;
    public Order placedOrder(int orderId) throws ResourceNotFoundException;
    public Order shippedOrder(int orderId) throws ResourceNotFoundException;
    public Order deliveredOrder(int orderId) throws ResourceNotFoundException;
    public Order canceledOrder(int orderId) throws ResourceNotFoundException;
    public List<Order> getAllOrders();

}

package com.sonam.ecommerce.ecommercebackend.service;

import com.sonam.ecommerce.ecommercebackend.entity.Order;
import com.sonam.ecommerce.ecommercebackend.entity.OrderStatus;
import com.sonam.ecommerce.ecommercebackend.entity.User;

import java.util.List;

public interface OrderService {

    public Order createOrder(User user);
    public void deleteOrder(int orderId);
    public Order placedOrder(int orderId);
    public Order shippedOrder(int orderId);
    public Order deliveredOrder(int orderId);
    public Order canceledOrder(int orderId);
    public List<Order> getAllOrders();

}

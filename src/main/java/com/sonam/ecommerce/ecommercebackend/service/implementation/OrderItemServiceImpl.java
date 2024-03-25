package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.entity.OrderItem;
import com.sonam.ecommerce.ecommercebackend.repository.OrderItemRepo;
import com.sonam.ecommerce.ecommercebackend.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemRepo orderItemRepo;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepo.save(orderItem);
    }
}

package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.entity.*;
import com.sonam.ecommerce.ecommercebackend.repository.OrderItemRepo;
import com.sonam.ecommerce.ecommercebackend.repository.OrderRepo;
import com.sonam.ecommerce.ecommercebackend.repository.UserRepo;
import com.sonam.ecommerce.ecommercebackend.service.CartService;
import com.sonam.ecommerce.ecommercebackend.service.OrderItemService;
import com.sonam.ecommerce.ecommercebackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepo orderRepo;
    @Autowired
    CartService cartService;
    @Autowired
    UserRepo userRepo;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderItemRepo orderItemRepo;

    @Override
    public Order createOrder(User user) {
        Cart cart = cartService.getCart(user.getUserId());
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem c : cart.getCartItems()){
            OrderItem orderItem = OrderItem.builder()
                    .price(c.getPrice())
                    .book(c.getBook())
                    .userId(c.getUserId())
                    .quantity(c.getQuantity())
                    .build();
            OrderItem createdOrderItem = orderItemRepo.save(orderItem);
            orderItems.add(createdOrderItem);

        }

        Order createdOrder = Order.builder()
                .user(user)
                .orderItems(orderItems)
                .totalPrice(cart.getTotalPrice())
                .totalItem(cart.getTotalItem())
                .shippingAddress("New York")
                .orderDate(LocalDateTime.now())
                .build();
        Order savedOrder = orderRepo.save(createdOrder);
        for(OrderItem orderItem : orderItems){
            orderItem.setOrder(savedOrder);
            orderItemRepo.save(orderItem);
        }

        // clear cart after creating the order
        cart.getCartItems().clear();
       cartService.updateCart(cart);
        return savedOrder;
    }

    @Override
    public void deleteOrder(int orderId) {

    }

    @Override
    public Order placedOrder(int orderId) {
        return null;
    }

    @Override
    public Order shippedOrder(int orderId) {
        return null;
    }

    @Override
    public Order deliveredOrder(int orderId) {
        return null;
    }

    @Override
    public Order canceledOrder(int orderId) {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }
}

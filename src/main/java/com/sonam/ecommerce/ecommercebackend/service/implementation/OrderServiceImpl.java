package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.entity.*;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.repository.OrderItemRepo;
import com.sonam.ecommerce.ecommercebackend.repository.OrderRepo;
import com.sonam.ecommerce.ecommercebackend.repository.UserRepo;
import com.sonam.ecommerce.ecommercebackend.service.CartItemService;
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
    @Autowired
    CartItemServiceImpl cartItemService;

    @Override
    public Order createOrder(User user) {
        Cart cart = cartService.getCart(user.getUserId());
        // create order items
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
        // save order items
        orderItems = orderItemRepo.saveAll(orderItems);

        // create order
        Order createdOrder = Order.builder()
                .user(user)
                //.orderItems(orderItems)
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
    public void deleteOrder(int orderId) throws ResourceNotFoundException {
        Order order = orderRepo.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Could not find order for id::"+orderId));
        orderRepo.delete(order);
    }

    @Override
    public Order confirmedOrder(int orderId) throws ResourceNotFoundException {
        Order order = orderRepo.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Could not find order for id::"+orderId));
        order.setOrderStatus(OrderStatus.CONFIRMED);
        return orderRepo.save(order);
    }

    @Override
    public Order findOrderById(int orderId) throws ResourceNotFoundException {
        return orderRepo.findById(orderId).get();
    }


    @Override
    public Order placedOrder(int orderId) throws ResourceNotFoundException {
        Order order = orderRepo.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Could not find order for id::"+orderId));
        order.setOrderStatus(OrderStatus.PLACED);
        return order;
    }

    @Override
    public Order shippedOrder(int orderId) throws ResourceNotFoundException {
        Order order = orderRepo.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Could not find order for id::"+orderId));
        order.setOrderStatus(OrderStatus.SHIPPED);
        return orderRepo.save(order);
    }

    @Override
    public Order deliveredOrder(int orderId) throws ResourceNotFoundException {
        Order order = orderRepo.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Could not find order for id::"+orderId));
        order.setOrderStatus(OrderStatus.DELIVERED);
        return orderRepo.save(order);
    }

    @Override
    public Order canceledOrder(int orderId) throws ResourceNotFoundException {
        Order order = orderRepo.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Could not find order for id::"+orderId));
        order.setOrderStatus(OrderStatus.CANCELED);
        return orderRepo.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
}

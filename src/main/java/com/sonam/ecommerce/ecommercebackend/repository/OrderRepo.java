package com.sonam.ecommerce.ecommercebackend.repository;

import com.sonam.ecommerce.ecommercebackend.entity.Order;
import com.sonam.ecommerce.ecommercebackend.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o where o.user.userId=:userId and (o.orderStatus = 'PLACED' or o.orderStatus = 'CONFIRMED' or o.orderStatus = 'SHIPPED' or o.orderStatus = 'DELIVERED')")
    public List<Order> getUsersOrders(@Param("userId") int userId);

//    List<Order> findByUserIdAndOrderStatusIn(int userId, List<OrderStatus> statuses);
}

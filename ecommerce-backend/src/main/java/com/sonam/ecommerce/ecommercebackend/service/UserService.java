package com.sonam.ecommerce.ecommercebackend.service;

import com.sonam.ecommerce.ecommercebackend.entity.User;

import java.util.List;

public interface UserService {
    public User register(User user);
    public User login(User user);
    public void userExists(int userId);
    public User findUser(int userId);
    public List<User> findUsers();

}

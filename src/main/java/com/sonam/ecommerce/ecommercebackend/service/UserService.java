package com.sonam.ecommerce.ecommercebackend.service;

import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;

import java.util.List;

public interface UserService {

    public void userExists(int userId) throws ResourceNotFoundException;
    public User findUser(int userId) throws ResourceNotFoundException;
    public List<User> findUsers();

    public String deleteUserAccount(int userId) throws ResourceNotFoundException;

    public User updateUserAccount(User user);

}

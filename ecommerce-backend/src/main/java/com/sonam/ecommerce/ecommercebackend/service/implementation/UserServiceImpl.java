package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.repository.UserRepo;
import com.sonam.ecommerce.ecommercebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserRepo userRepo;

    @Override
    public User register(User user) {
        return userRepo.save(user);   // save to db
    }

    @Override
    public User login(User user) {
        return userRepo.findByEmail(user.getEmail());
    }

    @Override
    public void userExists(int userId) {
        userRepo.findById(userId);

    }

    @Override
    public User findUser(int userId) {
        return userRepo.findById(userId).get();
    }

    @Override
    public List<User> findUsers() {
        return userRepo.findAll();  // return list of users from db
    }
}

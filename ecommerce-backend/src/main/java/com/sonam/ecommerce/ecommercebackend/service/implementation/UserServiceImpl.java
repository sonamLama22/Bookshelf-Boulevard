package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.repository.UserRepo;
import com.sonam.ecommerce.ecommercebackend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        //user.setUserId(UUID.randomUUID().hashCode());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public List<User> findUsers() {
        return userRepo.findAll();  // return list of users from db
    }
}

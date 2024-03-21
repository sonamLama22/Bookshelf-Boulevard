package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.repository.UserRepo;
import com.sonam.ecommerce.ecommercebackend.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserRepo userRepo;

    @Override
    public void userExists(int userId) throws ResourceNotFoundException {
        userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found for this id::"+userId));

    }

    @Override
    public User findUser(int userId) throws ResourceNotFoundException {
        return userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found for this id::"+userId));
    }

    @Override
    public List<User> findUsers() {
        return userRepo.findAll();  // return list of users from db
    }

}

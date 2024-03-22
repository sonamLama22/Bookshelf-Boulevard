package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.dto.UserDto;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.repository.UserRepo;
import com.sonam.ecommerce.ecommercebackend.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public String deleteUserAccount(int userId) throws ResourceNotFoundException {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found for id::"+userId));
        userRepo.delete(user);
        return "Your account has been deleted.";
    }

    @Override
    public User updateUserAccount(User updatedUser) {
        return userRepo.save(updatedUser);
    }

}

package com.sonam.ecommerce.ecommercebackend.controllers;

import com.sonam.ecommerce.ecommercebackend.dto.UserDto;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.service.implementation.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminApiController {
    @Autowired
    private UserServiceImpl userService;

    // http://localhost:8080/api/admin/profile
    @GetMapping("/profile")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("Welcome to admin profile");
    }

    // http://localhost:8080/api/admin/users
    @GetMapping("/getUsers")
    public ResponseEntity<?> getUsers(){
        List<User> list = userService.findUsers();
        System.out.println(list);
        return new ResponseEntity<>(HttpStatus.OK).ok(list);
    }

    // http://localhost:8080/api/admin/getUserInfo/{userId}
    @GetMapping("/getUserInfo/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable int userId) throws ResourceNotFoundException {
        User user = userService.findUser(userId);
        if(user != null){
            UserDto showUser = new UserDto();
            BeanUtils.copyProperties(user, showUser);
            return new ResponseEntity<>(showUser, HttpStatus.OK);
        }
        return new ResponseEntity<>("User doesnot exist.", HttpStatus.NOT_FOUND);
    }

}

package com.sonam.ecommerce.ecommercebackend.controllers;

import com.sonam.ecommerce.ecommercebackend.model.JwtRequest;
import com.sonam.ecommerce.ecommercebackend.model.JwtResponse;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.security.JwtAuthenticationFilter;
import com.sonam.ecommerce.ecommercebackend.security.JwtHelper;
import com.sonam.ecommerce.ecommercebackend.service.implementation.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/profile")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("Welcome to user profile");
    }

}

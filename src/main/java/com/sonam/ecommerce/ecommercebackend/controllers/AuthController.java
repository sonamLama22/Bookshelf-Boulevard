package com.sonam.ecommerce.ecommercebackend.controllers;

import com.sonam.ecommerce.ecommercebackend.dto.JwtAuthResponse;
import com.sonam.ecommerce.ecommercebackend.dto.RefreshTokenRequest;
import com.sonam.ecommerce.ecommercebackend.dto.SignInRequest;
import com.sonam.ecommerce.ecommercebackend.dto.SignUpRequest;
import com.sonam.ecommerce.ecommercebackend.service.CartService;
import com.sonam.ecommerce.ecommercebackend.service.implementation.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    // http://localhost:8080/api/auth/register
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
//        User registeredUser = authenticationService.signup(signUpRequest);
        JwtAuthResponse registeredUser = authenticationService.signup(signUpRequest);
        return new ResponseEntity<>(HttpStatus.OK).ok(registeredUser);
    }

    // http://localhost:8080/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> loginUser(@RequestBody SignInRequest signInRequest){
        return new ResponseEntity<>(HttpStatus.OK).ok(authenticationService.signin(signInRequest));
    }

    // http://localhost:8080/api/auth/refresh
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return new ResponseEntity<>(HttpStatus.OK).ok(authenticationService.refreshToken(refreshTokenRequest));
    }


}

package com.sonam.ecommerce.ecommercebackend.controllers;

import com.sonam.ecommerce.ecommercebackend.dto.*;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.repository.UserRepo;
import com.sonam.ecommerce.ecommercebackend.security.JwtHelper;
import com.sonam.ecommerce.ecommercebackend.service.CartService;
import com.sonam.ecommerce.ecommercebackend.service.implementation.AuthenticationServiceImpl;
import com.sonam.ecommerce.ecommercebackend.service.implementation.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationServiceImpl authenticationService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtHelper jwtHelper;

    // http://localhost:8080/api/auth/register
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
//        User registeredUser = authenticationService.signup(signUpRequest);
        var user = userRepo.findByEmail(signUpRequest.getEmail());
        if(user.isPresent()){
            return new ResponseEntity<>("This email already exists.", HttpStatus.BAD_REQUEST);
        }
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

    @GetMapping("/profile/{userId}")
    @PreAuthorize("#userId == authentication.principal.userId")
    // userId of the authenticated user must match the incoming userId in the request.
    public ResponseEntity<?> welcome(@PathVariable int userId) throws ResourceNotFoundException {
        // fetch the user details associated with the userId
        User user = userService.findUser(userId);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
//        String message = String.format("Welcome to %s's profile.", user.getUsername());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }




}

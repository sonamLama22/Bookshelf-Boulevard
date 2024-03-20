package com.sonam.ecommerce.ecommercebackend.service;

import com.sonam.ecommerce.ecommercebackend.dto.JwtAuthResponse;
import com.sonam.ecommerce.ecommercebackend.dto.RefreshTokenRequest;
import com.sonam.ecommerce.ecommercebackend.dto.SignInRequest;
import com.sonam.ecommerce.ecommercebackend.dto.SignUpRequest;
import com.sonam.ecommerce.ecommercebackend.entity.User;

public interface AuthenticationService {

    public User signup(SignUpRequest signUpRequest);
    public JwtAuthResponse signin(SignInRequest signInRequest);
    public JwtAuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}

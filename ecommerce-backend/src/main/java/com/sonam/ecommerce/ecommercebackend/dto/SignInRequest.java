package com.sonam.ecommerce.ecommercebackend.dto;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}

package com.sonam.ecommerce.ecommercebackend.dto;

import lombok.Data;

@Data
public class JwtAuthResponse {

    private String token;
    private String refershToken;
}

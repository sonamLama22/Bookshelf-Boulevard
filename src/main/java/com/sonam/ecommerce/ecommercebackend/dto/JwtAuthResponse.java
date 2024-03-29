package com.sonam.ecommerce.ecommercebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtAuthResponse {

    private String token;
    private String refreshToken;
//    private String username;
    private String email;
    private int id;
    private String role;
}

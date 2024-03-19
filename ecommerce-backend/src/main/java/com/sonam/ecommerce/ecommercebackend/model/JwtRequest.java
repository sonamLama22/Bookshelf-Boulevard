package com.sonam.ecommerce.ecommercebackend.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtRequest {
    private String email;
    private String password;
}

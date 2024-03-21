package com.sonam.ecommerce.ecommercebackend.service;

import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;

public interface TokenService {

    public String tokenExists(int userId) throws ResourceNotFoundException ;

}

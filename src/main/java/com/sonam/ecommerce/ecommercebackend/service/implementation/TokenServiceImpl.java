package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.entity.Token;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.repository.TokenRepo;
import com.sonam.ecommerce.ecommercebackend.service.TokenService;
import com.sonam.ecommerce.ecommercebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepo tokenRepo;

    @Autowired
    private UserService userService;

    @Transactional
    @Override
    public String tokenExists(int userId) throws ResourceNotFoundException {
        User user = userService.findUser(userId);
        System.out.println("user--"+user);
        var validUserToken = tokenRepo.finAllValidTokensOfUser(userId);
        System.out.println("Valid tokens----"+validUserToken);

        var token = tokenRepo.findByToken(validUserToken.get(0).getToken());
        String tokenString="";
        if(token.isPresent()){ // check if Optional contains a value
            var extractedToken = token.get(); // token object
            tokenString = extractedToken.getToken(); // token value
            System.out.println("Token string--"+tokenString);
        }
        return tokenString;
    }
}

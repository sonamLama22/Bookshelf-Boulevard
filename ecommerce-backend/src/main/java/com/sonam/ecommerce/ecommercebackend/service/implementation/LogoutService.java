package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.repository.TokenRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    @Autowired
    private TokenRepo tokenRepo;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String requestHeader = request.getHeader("Authorization");
        String token;
        //If 'Authorization' and 'Bearer' are found, validate and authenticate the token
        if (requestHeader == null && !requestHeader.startsWith("Bearer")) {
            return;
        }
        token = requestHeader.substring(7);
        var storedToken = tokenRepo.findByToken(token).orElse(null);
        System.out.println("StoredToken --> "+storedToken);
        if(storedToken != null){
            storedToken.setExpired(true);
            tokenRepo.save(storedToken);
        }
}
}

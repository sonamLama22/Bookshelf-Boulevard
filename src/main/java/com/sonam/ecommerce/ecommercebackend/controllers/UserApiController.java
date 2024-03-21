package com.sonam.ecommerce.ecommercebackend.controllers;

import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.ResourceNotFoundException;
import com.sonam.ecommerce.ecommercebackend.repository.TokenRepo;
import com.sonam.ecommerce.ecommercebackend.service.implementation.TokenServiceImpl;
import com.sonam.ecommerce.ecommercebackend.service.implementation.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TokenServiceImpl tokenService;

    @Autowired
    private TokenRepo tokenRepo;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<String> welcome(@PathVariable int userId,@RequestHeader("Authorization")  String authToken) throws ResourceNotFoundException {

        //extract token from header
        String tokenFromHeader = authToken.replace("Bearer ",""); // Remove "Bearer " prefix

        // check if token associated with this userId exists.
        String tokenInDB = tokenService.tokenExists(userId);
        System.out.println("Token in db -->"+tokenInDB);

        // Check if the token string matches the token of the userId
        if (!tokenFromHeader.equals(tokenInDB)) {
            // Return unauthorized response if the tokens don't match
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        // fetch the user details associated with the userId
        User user = userService.findUser(userId);
        String message = String.format("Welcome to %s's profile.", user.getUsername());
        return ResponseEntity.ok(message);
    }

}

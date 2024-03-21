package com.sonam.ecommerce.ecommercebackend.controllers;

import com.sonam.ecommerce.ecommercebackend.entity.Role;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/profile/{userId}")
    @PreAuthorize("#userId == authentication.principal.userId")
    // userId of the authenticated user must match the incoming userId in the request.
    public ResponseEntity<String> welcome(@PathVariable int userId) throws ResourceNotFoundException {

//        //extract token from header
//        String tokenFromHeader = authToken.replace("Bearer ",""); // Remove "Bearer " prefix
//
//        // check if token associated with this userId exists.
//        String tokenInDB = tokenService.tokenExists(userId);
//        System.out.println("Token in db -->"+tokenInDB);
//
//        // Check if the token string matches the token of the userId
//        if (!tokenFromHeader.equals(tokenInDB)) {
//            // Return unauthorized response if the tokens don't match
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
//        }

        // fetch the user details associated with the userId
        User user = userService.findUser(userId);
        String message = String.format("Welcome to %s's profile.", user.getUsername());
        return ResponseEntity.ok(message);
    }

    // User can delete their account
    @DeleteMapping("/{userId}/profile/delete")
    @PreAuthorize("#userId == authentication.principal.userId")
    public ResponseEntity<?> deleteAccount(@PathVariable int userId) throws ResourceNotFoundException {
        String message = userService.deleteUserAccount(userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // User can update their account
    @PutMapping("/{userId}/profile/update")
    @PreAuthorize("#userId == authentication.principal.userId")
    public ResponseEntity<?> updateUserAccount(@RequestBody User user, @PathVariable int userId) throws ResourceNotFoundException {
        User incomingUser = userService.findUser(userId);
        if(incomingUser != null){
            User updatedUser = User.builder()
      //              .userId(user.getUserId())
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .username(user.getUsername())
                    .role(Role.USER)
                    .build();
            userService.deleteUserAccount(userId); // delete previous records associated with this id.
            return new ResponseEntity<>(userService.updateUserAccount(updatedUser), HttpStatus.OK);
        }
        return new ResponseEntity<>("Your account could not be updated.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

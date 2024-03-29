package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Spring Security
// Responsible for loading user details and creating userDetails object for auth purposes.
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    // Called by spring security to retrieve user details for authentication.
    // It looks up the user in userRepo.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // load user from db

        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found: " + username));

        return user; //return object.
    }
}

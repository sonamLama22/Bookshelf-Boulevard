package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.dto.JwtAuthResponse;
import com.sonam.ecommerce.ecommercebackend.dto.RefreshTokenRequest;
import com.sonam.ecommerce.ecommercebackend.dto.SignInRequest;
import com.sonam.ecommerce.ecommercebackend.dto.SignUpRequest;
import com.sonam.ecommerce.ecommercebackend.entity.Role;
import com.sonam.ecommerce.ecommercebackend.entity.Token;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.repository.TokenRepo;
import com.sonam.ecommerce.ecommercebackend.repository.UserRepo;
import com.sonam.ecommerce.ecommercebackend.security.JwtHelper;
import com.sonam.ecommerce.ecommercebackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtHelper jwtHelper;

    private final TokenRepo tokenRepo;


    @Override
    public JwtAuthResponse signup(SignUpRequest signUpRequest){
        User user = new User();
        user.setUserId(UUID.randomUUID().hashCode());
        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getUsername());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        var savedUser = userRepo.save(user);
        var jwt = jwtHelper.generateToken(user);

        // persist generated token in database.
        saveUserToken(savedUser, jwt);

        var refreshToken = jwtHelper.generateRefreshToken(new HashMap<>(), user);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(jwt);
        jwtAuthResponse.setRefreshToken(refreshToken);
        return jwtAuthResponse;
    }

    @Override
    public JwtAuthResponse signin(SignInRequest signInRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(),
                signInRequest.getPassword()));
        var user = userRepo.findByEmail(signInRequest.getEmail()).orElseThrow(()-> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtHelper.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user,jwt);

        var refreshToken = jwtHelper.generateRefreshToken(new HashMap<>(), user);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(jwt);
        jwtAuthResponse.setRefreshToken(refreshToken);
        return jwtAuthResponse;
    }

    @Override
    public JwtAuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtHelper.getUsernameFromToken(refreshTokenRequest.getToken());
        User user = userRepo.findByEmail(userEmail).orElseThrow();
        // validate refresh token
        if(jwtHelper.validateToken(refreshTokenRequest.getToken(), user)){
            var jwt = jwtHelper.generateToken(user); // generate new jwt token with new expiration date.

            revokeAllUserTokens(user);
            saveUserToken(user,jwt);

            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setToken(jwt);
            jwtAuthResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthResponse;
        }
        return null;
    }

    // set all tokens as expired, so that a user can have only one valid token at a given time.
    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepo.finAllValidTokensOfUser(user.getUserId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);

        });
        tokenRepo.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwt) {
        var token = Token.builder()
                .user(user)
                .token(jwt)
                .expired(false)
                .build();
        tokenRepo.save(token);
    }

}

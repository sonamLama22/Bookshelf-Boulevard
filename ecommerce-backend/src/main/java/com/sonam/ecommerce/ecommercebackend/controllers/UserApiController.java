package com.sonam.ecommerce.ecommercebackend.controllers;

import com.sonam.ecommerce.ecommercebackend.model.JwtRequest;
import com.sonam.ecommerce.ecommercebackend.model.JwtResponse;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.security.JwtAuthenticationFilter;
import com.sonam.ecommerce.ecommercebackend.security.JwtHelper;
import com.sonam.ecommerce.ecommercebackend.service.implementation.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserApiController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper jwtHelper;


    // http://localhost:8080/api/auth/register
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user){
        User registeredUser = userService.register(user);
        return new ResponseEntity<>(HttpStatus.OK).ok(registeredUser);
    }

//    // http://localhost:8080/api/auth/login
//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@Valid @RequestBody User user){
//        User loggedUser = userService.login(user);
//        //System.out.println(loggedUser);
//        loginDto userDto = new loginDto();
//        BeanUtils.copyProperties(loggedUser, userDto); // only expose necessary details like id and name.
//        //System.out.println(userDto);
//        return new ResponseEntity<>(HttpStatus.OK).ok(userDto);
//    }

    //private Logger logger = LoggerFactory.getLogger(AuthController.class);

    // http://localhost:8080/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody JwtRequest user){

        this.doAuthenticate(user.getEmail(), user.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        System.out.println("->"+userDetails);
        String token = this.jwtHelper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // http://localhost:8080/api/auth/users
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(){
        List<User> list = userService.findUsers();
        return new ResponseEntity<>(HttpStatus.OK).ok(list);
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('USER')")
    public String userProfile(){
        return "Welcome to User profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminProfile(){
        return "Welcome to Admin profile";
    }
//
//    @PostMapping("/generateToken")
//    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(authRequest.getUsername());
//        } else {
//            throw new UsernameNotFoundException("invalid user request !");
//        }
//    }

    private void doAuthenticate(String email, String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        try{
            manager.authenticate(authenticationToken);
        }catch(BadCredentialsException e){
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler(){
        return "Invalid credentials";
    }
}

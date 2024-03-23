package com.sonam.ecommerce.ecommercebackend.repository;

import com.sonam.ecommerce.ecommercebackend.entity.Role;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    // Query methods
    public Optional<User> findByEmail(String email);
    public User findByRole(Role role);

}

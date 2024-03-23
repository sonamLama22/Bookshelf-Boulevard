package com.sonam.ecommerce.ecommercebackend;

import com.sonam.ecommerce.ecommercebackend.entity.Role;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EntityScan( basePackages = {"com.*"} )
@EnableJpaRepositories( basePackages = {"com.*"} )
public class EcommerceBackendApplication implements CommandLineRunner {

	@Autowired
	private UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceBackendApplication.class, args);
	}

	// Only one admin
	public void run (String... args){
		User adminAccount = userRepo.findByRole(Role.ADMIN);
		if(null == adminAccount){
			User user = new User();
			user.setEmail("admin@gmail.com");
			user.setUsername("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin12345"));
			userRepo.save(user);
		}

	}

}

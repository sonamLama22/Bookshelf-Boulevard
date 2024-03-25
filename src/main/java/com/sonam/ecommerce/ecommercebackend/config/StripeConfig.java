package com.sonam.ecommerce.ecommercebackend.config;

import com.stripe.Stripe;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    @Autowired
    private Dotenv dotenv; // use this bean to access variable from '.env' file

    @Bean
    public void initStripe(){
        Stripe.apiKey = dotenv.get("SECRET_API_KEY");
    }
}

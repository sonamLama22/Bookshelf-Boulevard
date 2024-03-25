package com.sonam.ecommerce.ecommercebackend.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Value( "${stripe.currency}")
    private String currency;
}

package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.dto.PaymentDto;
import com.sonam.ecommerce.ecommercebackend.entity.Payment;
import com.sonam.ecommerce.ecommercebackend.entity.User;
import com.sonam.ecommerce.ecommercebackend.exception.PaymentException;
import com.sonam.ecommerce.ecommercebackend.repository.PaymentRepo;
import com.sonam.ecommerce.ecommercebackend.repository.UserRepo;
import com.sonam.ecommerce.ecommercebackend.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    PaymentRepo paymentRepo;



    @Override
    public void stripePayment(String userEmail) {

    }
}

package com.sonam.ecommerce.ecommercebackend.service;

import com.sonam.ecommerce.ecommercebackend.dto.PaymentDto;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface PaymentService {

    public Double findPaymentFeesByPersonEmail(String userEmail);
    public PaymentIntent createPaymentIntent(PaymentDto paymentDto) throws StripeException;
    public void stripePayment(String userEmail);
}

package com.sonam.ecommerce.ecommercebackend.service;

import com.sonam.ecommerce.ecommercebackend.dto.PaymentDto;
import com.sonam.ecommerce.ecommercebackend.entity.Order;
import com.stripe.exception.StripeException;

public interface PaymentService {

        public PaymentDto addPaymentLink(Order order) throws StripeException;
}

package com.sonam.ecommerce.ecommercebackend.service.implementation;

import com.sonam.ecommerce.ecommercebackend.dto.PaymentDto;
import com.sonam.ecommerce.ecommercebackend.entity.Order;
import com.sonam.ecommerce.ecommercebackend.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.secretKey}")
    private String stripeSecretKey;
//    private String stripeSecretKey = System.getenv("SECRET_API_KEY");

    @Override
    public PaymentDto addPaymentLink(Order order) throws StripeException {

        Stripe.apiKey = stripeSecretKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/payment-sucess" + order.getId())
                .setCancelUrl("http://localhost:5173/payment-fail")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("USD")
                                .setUnitAmount((long) order.getTotalPrice()*100)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Bookshelf Boulevard")
                                        .build())
                                .build())
                        .build())
                .build();

        Session session = Session.create(params);
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPayment_url(session.getUrl());

        return paymentDto;
    }
}

package com.sonam.ecommerce.ecommercebackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "amount")
    private double amount;

    @OneToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    // must create an index on 'email' column of the 'users' table to improve query performance
    // CREATE INDEX idx_email ON users (email);
    private User paymentHolder;

}

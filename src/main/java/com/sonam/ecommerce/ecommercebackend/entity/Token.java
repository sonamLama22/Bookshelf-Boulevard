package com.sonam.ecommerce.ecommercebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {

    @Id
    @GeneratedValue
    private Integer id;
    private String token;

    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // prevent infinite loop
    private User user;
}

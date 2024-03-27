package com.bluewalnut.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class TokenT {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long seq;

    private String checkoutId;
    private String token;

    public TokenT(String checkoutId, String token) {
        this.checkoutId = checkoutId;
        this.token = token;
    }
}

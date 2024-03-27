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
@Table(name = "PAYMENT_ORDER")
public class PaymentOrderT {

    @Id
    private String paymentOrderId;

    private String amount;
    private String currency;
    private String checkoutId;
    private String paymentOrderStatus; // Not Started, Executing, Success, Failed
}

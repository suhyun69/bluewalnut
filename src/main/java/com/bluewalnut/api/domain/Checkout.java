package com.bluewalnut.api.domain;

import com.bluewalnut.api.entity.CheckoutT;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class Checkout {
    private String id;
    private String cardRefId;
    private String currency;
    private String amount;
    private String status;

    public Checkout(CheckoutT t) {
        this.id = t.getId();
        this.cardRefId = t.getCardRefId();
        this.currency = t.getCurrency();
        this.amount = t.getAmount();
        this.status = t.getStatus();
    }
}

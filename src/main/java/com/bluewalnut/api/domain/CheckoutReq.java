package com.bluewalnut.api.domain;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@RequiredArgsConstructor
public class CheckoutReq {
    private String ci;
    private String cardRefId;
    private String currency;
    private String amount;
    private CheckoutStatus status; // Not Started, Executing, Success, Failed
    private LocalDateTime expirationTime;
}

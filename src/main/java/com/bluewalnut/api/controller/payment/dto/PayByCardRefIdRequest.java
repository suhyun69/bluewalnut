package com.bluewalnut.api.controller.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayByCardRefIdRequest {
    private String ci;
    private String cardRefId;
    private String amount;
}

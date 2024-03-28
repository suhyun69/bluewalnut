package com.bluewalnut.api.controller.user.dto;

import com.bluewalnut.api.domain.Checkout;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindCheckoutResponse {
    private List<Checkout> checkouts;
}

package com.bluewalnut.api.domain;

import java.util.Arrays;

public enum CheckoutStatus {
    NotStarted,
    Executing,
    Success,
    Failed;

    public static CheckoutStatus of(String status) {
        return Arrays.stream(CheckoutStatus.values())
                .filter(v -> v.name().equals(status))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 CheckoutStatus 코드입니다."));
    }
}

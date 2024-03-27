package com.bluewalnut.api.config.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    REQUEST_TOKEN_DUPLICATED(409, "중복된 토큰 발행 요청입니다."),
    CARD_DUPLICATED(409, "이미 저장된 카드입니다."),
    CHECKOUT_NOT_FOUND(409, "결제요청 정보를 찾을 수 없습니다."),
    TOKEN_MAPPING_FAIL(409, "토큰 생성에 실패했습니다."),
    TOKEN_NOT_FOUND(409, "토큰을 찾을 수 없습니다."),
    CARD_NOT_FOUND(409, "카드를 찾을 수 없습니다.")
    ; // End

    private final int status;
    private final String message;

    // 생성자 구성
    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
}

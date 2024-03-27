package com.bluewalnut.api.config.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    TOKEN_PUBLISH_REQ_DUPLICATED(409, "중복된 토큰 발행 요청입니다."),
    CARD_DUPLICATED(409, "이미 저장된 카드입니다.")
    ; // End

    private final int status;
    private final String message;

    // 생성자 구성
    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
}

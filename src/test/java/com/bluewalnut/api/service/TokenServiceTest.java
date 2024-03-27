package com.bluewalnut.api.service;

import com.bluewalnut.api.config.exception.ErrorCode;
import com.bluewalnut.api.repository.TokenRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TokenServiceTest {

    private static String DUMMY_CHECKOUT_ID = "12345";

    @Autowired
    private TokenService tokenService;

    @Test
    @DisplayName("Token 발행")
    @Transactional
    void token_publish() {

        String token = tokenService.publish(DUMMY_CHECKOUT_ID);
        assert(token.length() == "56f3234d-ad68-4d8d-9ba5-c96a9b9fe124".length());
    }

    @Test
    @DisplayName("Token 발행 - 중복요청")
    @Transactional
    void token_publish_duplicated() {

        tokenService.publish(DUMMY_CHECKOUT_ID);

        try {
            tokenService.publish(DUMMY_CHECKOUT_ID);
        }
        catch (Exception ex) {
            assert(ex.getMessage().equals(ErrorCode.TOKEN_PUBLISH_REQ_DUPLICATED.getMessage()));
        }
    }

}
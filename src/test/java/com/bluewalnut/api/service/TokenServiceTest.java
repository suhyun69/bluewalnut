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

    private static String DUMMY_CI = "A12345";
    private static String DUMMY_CARD_NO = "1234-5678-9012-3456";

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

    @Test
    @DisplayName("카드 등록")
    @Transactional
    void card_enroll() {

        tokenService.saveCard(DUMMY_CI, DUMMY_CARD_NO);
        assert(tokenService.findCard(DUMMY_CI).size() == 1);
    }

    @Test
    @DisplayName("카드 등록 - 중복")
    @Transactional
    void card_enroll_duplicated() {

        tokenService.saveCard(DUMMY_CI, DUMMY_CARD_NO);

        try {
            tokenService.saveCard(DUMMY_CI, DUMMY_CARD_NO);
        }
        catch (Exception ex) {
            assert(ex.getMessage().equals(ErrorCode.CARD_DUPLICATED.getMessage()));
        }
    }

}
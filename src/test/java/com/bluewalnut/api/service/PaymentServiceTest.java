package com.bluewalnut.api.service;

import com.bluewalnut.api.config.exception.BusinessException;
import com.bluewalnut.api.config.exception.ErrorCode;
import com.bluewalnut.api.entity.CheckoutT;
import com.bluewalnut.api.repository.CheckoutRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class PaymentServiceTest {

    private final TokenService tokenService = Mockito.mock(TokenService.class);
    private final PGService pgService = Mockito.mock(PGService.class);
    private final CheckoutRepository checkoutRepository = Mockito.mock(CheckoutRepository.class);
    private final PaymentService paymentService = new PaymentService(tokenService, pgService, checkoutRepository);

    private static String DUMMY_CARD_NO = "1234-5678-9012-3456";
    private static String DUMMY_CI = "A12345";
    private static String DUMMY_AMOUNT = "10000";

    @Test
    @DisplayName("카드 등록")
    @Transactional
    void payByCardRefId() {

        given(tokenService.findCard(any())).willReturn(Arrays.asList(DUMMY_CARD_NO));
        given(checkoutRepository.save(any())).willReturn(new CheckoutT());
        assert(paymentService.payByCardRefId(DUMMY_CI, DUMMY_CARD_NO, DUMMY_AMOUNT).length() == "56f3234d-ad68-4d8d-9ba5-c96a9b9fe124".length());
    }

    @Test
    @DisplayName("카드 등록__등록된_카드없음")
    @Transactional
    void payByCardRefId__CARD_NOT_FOUND() {

        given(tokenService.findCard(any())).willReturn(new ArrayList<>());
        assertThatThrownBy(() -> paymentService.payByCardRefId(any(), any(), any()))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.CARD_NOT_FOUND.getMessage());
    }

}
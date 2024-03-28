package com.bluewalnut.api.service;

import com.bluewalnut.api.config.exception.BusinessException;
import com.bluewalnut.api.config.exception.ErrorCode;
import com.bluewalnut.api.domain.CheckoutStatus;
import com.bluewalnut.api.entity.CheckoutT;
import com.bluewalnut.api.entity.TokenT;
import com.bluewalnut.api.repository.CardRepository;
import com.bluewalnut.api.repository.CheckoutRepository;
import com.bluewalnut.api.repository.TokenRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class TokenServiceTest {

    private static String DUMMY_CHECKOUT_ID = "12345";
    private static String DUMMY_CI = "A12345";
    private static String DUMMY_CARD_NO = "1234-5678-9012-3456";

    private final TokenRepository tokenRepository = Mockito.mock(TokenRepository.class);
    private final CardRepository cardRepository = Mockito.mock(CardRepository.class);
    private final CheckoutRepository checkoutRepository = Mockito.mock(CheckoutRepository.class);
    private final TokenService tokenMockService = new TokenService(tokenRepository, cardRepository, checkoutRepository);

    @Autowired
    private TokenService tokenService;

    @Test
    @DisplayName("카드 등록")
    @Transactional
    void requestCardRefId() {

        tokenService.requestCardRefId(DUMMY_CI, DUMMY_CARD_NO);
        assert(tokenService.findCard(DUMMY_CI).size() == 1);
    }

    @Test
    @DisplayName("카드 등록 - 중복")
    @Transactional
    void requestCardRefId_duplicated() {

        tokenService.requestCardRefId(DUMMY_CI, DUMMY_CARD_NO);

        assertThatThrownBy(() -> tokenService.requestCardRefId(DUMMY_CI, DUMMY_CARD_NO))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.CARD_DUPLICATED.getMessage());
    }

    @Test
    @DisplayName("Token 발행")
    @Transactional
    void requestToken() {

        CheckoutT checkoutT = Mockito.mock(CheckoutT.class);
        given(checkoutRepository.findById(any())).willReturn(Optional.ofNullable(checkoutT));
        given(checkoutRepository.save(any())).willReturn(checkoutT);

        given(tokenRepository.existsByCheckoutId(any())).willReturn(false);
        given(tokenRepository.save(any())).willReturn(new TokenT());

        String token = tokenMockService.requestToken(DUMMY_CHECKOUT_ID);
        assert(token.length() == "56f3234d-ad68-4d8d-9ba5-c96a9b9fe124".length());
    }

    @Test
    @DisplayName("Token 발행 - 중복요청")
    @Transactional
    void requestToken_duplicated() {

        given(checkoutRepository.findById(any(String.class))).willReturn(Optional.ofNullable(new CheckoutT()));
        given(tokenRepository.existsByCheckoutId(any(String.class))).willReturn(true);

        assertThatThrownBy(() -> tokenMockService.requestToken(DUMMY_CHECKOUT_ID))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.REQUEST_TOKEN_DUPLICATED.getMessage());
    }

    @Test
    @DisplayName("Token 발행 - 매핑실패")
    @Transactional
    void requestToken__mapping_failed() {

        given(checkoutRepository.findById(any(String.class))).willReturn(Optional.ofNullable(new CheckoutT()));
        given(tokenRepository.existsByCheckoutId(any(String.class))).willReturn(false);
        given(tokenRepository.save(any())).willReturn(RuntimeException.class);

        assertThatThrownBy(() -> tokenMockService.requestToken(DUMMY_CHECKOUT_ID))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.TOKEN_MAPPING_FAIL.getMessage());
    }

    @Test
    @DisplayName("Token 유효성 체크")
    @Transactional
    void verifyToken() {

        given(tokenRepository.findById(any())).willReturn(Optional.of(new TokenT()));

        CheckoutT checkoutT = Mockito.mock(CheckoutT.class);
        given(checkoutT.getStatus()).willReturn(CheckoutStatus.Executing.name());
        given(checkoutT.getExpirationTime()).willReturn(LocalDateTime.now().plusMinutes(1));
        given(checkoutRepository.findById(any())).willReturn(Optional.ofNullable(checkoutT));

        assertThat(tokenMockService.verifyToken(any(String.class))).isEqualTo(true);
    }

    @Test
    @DisplayName("Token 유효성 체크 실패 - Status")
    @Transactional
    void verifyToken__status_failed() {

        given(tokenRepository.findById(any())).willReturn(Optional.of(new TokenT()));

        CheckoutT checkoutT = Mockito.mock(CheckoutT.class);
        given(checkoutT.getStatus()).willReturn(CheckoutStatus.NotStarted.name());
        given(checkoutRepository.findById(any())).willReturn(Optional.ofNullable(checkoutT));

        assertThat(tokenMockService.verifyToken(any(String.class))).isEqualTo(false);
    }

    @Test
    @DisplayName("Token 유효성 체크 실패 - expiration_date")
    @Transactional
    void verifyToken__expiration_date_failed() {

        given(tokenRepository.findById(any())).willReturn(Optional.of(new TokenT()));

        CheckoutT checkoutT = Mockito.mock(CheckoutT.class);
        given(checkoutT.getStatus()).willReturn(CheckoutStatus.Executing.name());
        given(checkoutT.getExpirationTime()).willReturn(LocalDateTime.now().minusMinutes(10));
        given(checkoutRepository.findById(any())).willReturn(Optional.ofNullable(checkoutT));

        assertThat(tokenMockService.verifyToken(any(String.class))).isEqualTo(false);
    }
}
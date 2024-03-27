package com.bluewalnut.api.service;


import com.bluewalnut.api.config.exception.BusinessException;
import com.bluewalnut.api.config.exception.ErrorCode;
import com.bluewalnut.api.domain.CheckoutStatus;
import com.bluewalnut.api.entity.CheckoutT;
import com.bluewalnut.api.repository.CheckoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TokenService tokenService;
    private final CheckoutRepository checkoutRepository;

    public String registryCard(String ci, String encryptedCardNo) {
        return tokenService.requestCardRefId(ci, encryptedCardNo);
    }

    public List<String> findCard(String ci) {
        return tokenService.findCard(ci);
    }

    public String payByCardRefId(String ci, String cardRefId, String amount) {

        CheckoutT checkoutT = CheckoutT.builder()
                .id(UUID.randomUUID().toString())
                .ci(ci)
                .cardRefId(cardRefId)
                .currency("KRW")
                .amount(amount)
                .status(CheckoutStatus.NotStarted.name()) // 결제 요청 상태 초기화 : NotStarted
                .expirationTime(LocalDateTime.now().plusMinutes(5)) // 결제 요청 만료일 : 현 시점 이후 5분
                .build();
        checkoutRepository.save(checkoutT); // 결제정보 저장

        // return tokenService.requestToken(checkoutT.getId()); // token 반환
        return checkoutT.getId(); // checkoutId 반환
    }

    public String findStatus(String checkoutId) {
        CheckoutT checkoutT = checkoutRepository.findById(checkoutId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CHECKOUT_NOT_FOUND));

        return CheckoutStatus.of(checkoutT.getStatus()).toString();
    }
}

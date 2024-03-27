package com.bluewalnut.api.service;

import com.bluewalnut.api.config.exception.BusinessException;
import com.bluewalnut.api.config.exception.ErrorCode;
import com.bluewalnut.api.domain.CheckoutReq;
import com.bluewalnut.api.domain.CheckoutStatus;
import com.bluewalnut.api.entity.CardT;
import com.bluewalnut.api.entity.CheckoutT;
import com.bluewalnut.api.entity.TokenT;
import com.bluewalnut.api.repository.CardRepository;
import com.bluewalnut.api.repository.CheckoutRepository;
import com.bluewalnut.api.repository.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final CardRepository cardRepository;
    private final CheckoutRepository checkoutRepository;

    public String requestCardRefId(String ci, String encryptedCardNo) {

        // 중복 체크
        if(cardRepository.existsByCardNo(encryptedCardNo)) {
            throw new BusinessException(ErrorCode.CARD_DUPLICATED);
        }

        // card_ref_id 생성 및 전달
        String cardRefId = String.valueOf(encryptedCardNo.hashCode());
        cardRepository.save(new CardT(ci, encryptedCardNo, cardRefId));

        return cardRefId;
    }

    public List<String> findCard(String ci) {
        List<CardT> cardTList = cardRepository.findAllByCi(ci);
        return cardTList.stream()
                .map(t -> t.getCardRefId())
                .collect(Collectors.toList());
    }

    public String requestToken(String checkoutId) {

        // 결제 요청 조회
        CheckoutT t = checkoutRepository.findById(checkoutId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CHECKOUT_NOT_FOUND));

        // 중복 체크
        if(tokenRepository.existsByCheckoutId(checkoutId)) {
            throw new BusinessException(ErrorCode.REQUEST_TOKEN_DUPLICATED);
        }

        // 1회용 토큰 생성
        String token = UUID.randomUUID().toString();
        try {
            // token ~ checkoutId 매핑 생성
            tokenRepository.save(new TokenT(token, t.getId()));
        }
        catch (Exception ex) {
            throw new BusinessException(ErrorCode.TOKEN_MAPPING_FAIL);
        }

        // 결제요청 상태 업데이트
        t.updateStatus(CheckoutStatus.Executing);
        checkoutRepository.save(t);

        return token;
    }
}

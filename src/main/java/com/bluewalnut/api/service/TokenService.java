package com.bluewalnut.api.service;

import com.bluewalnut.api.config.exception.BusinessException;
import com.bluewalnut.api.config.exception.ErrorCode;
import com.bluewalnut.api.entity.CardT;
import com.bluewalnut.api.entity.TokenT;
import com.bluewalnut.api.repository.CardRepository;
import com.bluewalnut.api.repository.TokenRepository;
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

    public String publish(String checkoutId) {

        // 중복 체크
        if(tokenRepository.existsByCheckoutId(checkoutId)) {
            throw new BusinessException(ErrorCode.TOKEN_PUBLISH_REQ_DUPLICATED);
        }

        // 1회용 토큰 생성 및 전달
        String token = UUID.randomUUID().toString();
        tokenRepository.save(new TokenT(checkoutId, token));

        return token;
    }

    public String saveCard(String ci, String cardNo) {

        // 중복 체크
        if(cardRepository.existsByCardNo(cardNo)) {
            throw new BusinessException(ErrorCode.CARD_DUPLICATED);
        }

        // ref_card_id 생성 및 전달
        String refCardId = String.valueOf(cardNo.hashCode());
        cardRepository.save(new CardT(ci, cardNo, refCardId));

        return refCardId;
    }

    public List<String> findCard(String ci) {
        List<CardT> cardTList = cardRepository.findAllByCi(ci);
        return cardTList.stream()
                .map(t -> t.getCardRefId())
                .collect(Collectors.toList());
    }
}

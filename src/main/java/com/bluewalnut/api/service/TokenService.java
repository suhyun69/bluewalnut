package com.bluewalnut.api.service;

import com.bluewalnut.api.config.exception.BusinessException;
import com.bluewalnut.api.config.exception.ErrorCode;
import com.bluewalnut.api.entity.TokenT;
import com.bluewalnut.api.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

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
}

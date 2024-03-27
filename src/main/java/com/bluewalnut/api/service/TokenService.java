package com.bluewalnut.api.service;

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

        // 1회용 토큰 생성 및 전달
        String token = UUID.randomUUID().toString();
        tokenRepository.save(new TokenT(checkoutId, token));

        return token;
    }
}

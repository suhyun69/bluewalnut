package com.bluewalnut.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PGService {

    private final TokenService tokenService;

    public boolean approvalToken(String token) {
        return tokenService.verifyToken(token);
    }
}

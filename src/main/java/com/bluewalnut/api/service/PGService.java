package com.bluewalnut.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PGService {

    private final TokenService tokenService;

    public Boolean approvalToken(String token) {
        Boolean isValid = tokenService.verifyToken(token);

        // Approval
        // 본래 별도의 결제 승인 과정이 있겠으나,
        // 여기서는 전달받은 token이 유효한지만 확인하는 것으로 과정을 대체한다.
        return isValid ? true : false;
    }
}

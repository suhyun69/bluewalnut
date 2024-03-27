package com.bluewalnut.api.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TokenService tokenService;

    public String enrollCard(String ci, String cardNo) {
        return tokenService.saveCard(ci, cardNo);
    }

    public List<String> findCard(String ci) {
        return tokenService.findCard(ci);
    }
}

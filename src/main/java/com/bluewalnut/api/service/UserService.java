package com.bluewalnut.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PaymentService paymentService;

    public String enrollCard(String ci, String cardNo) {
        String encryptedCardNo = cardNo;
        return paymentService.registryCard(ci, encryptedCardNo);
    }

    public List<String> findCard(String ci) {
        return paymentService.findCard(ci);
    }

    public void payment(String ci, String cardRefId, String amount) {
        paymentService.payByCardRefId(ci, cardRefId, amount);
    }
}

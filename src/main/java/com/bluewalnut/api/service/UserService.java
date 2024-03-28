package com.bluewalnut.api.service;

import com.bluewalnut.api.domain.Checkout;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PaymentService paymentService;
    private final PasswordEncoder passwordEncoder;

    public String registryCard(String ci, String cardNo) {
        String encryptedCardNo = passwordEncoder.encode(cardNo);
        return paymentService.registryCard(ci, encryptedCardNo);
    }

    public List<String> findCard(String ci) {
        return paymentService.findCard(ci);
    }

    public List<Checkout> findCheckout(String ci) {
        return paymentService.findCheckout(ci);
    }
}

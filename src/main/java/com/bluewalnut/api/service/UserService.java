package com.bluewalnut.api.service;

import com.bluewalnut.api.config.AESUtil;
import com.bluewalnut.api.config.exception.BusinessException;
import com.bluewalnut.api.config.exception.ErrorCode;
import com.bluewalnut.api.domain.Checkout;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PaymentService paymentService;
    // private final PasswordEncoder passwordEncoder;
    private final AESUtil aesUtil;

    public String registryCard(String ci, String cardNo) {
        String encryptedCardNo = cardNo;

        try {
            encryptedCardNo = aesUtil.encrypt(cardNo);
        }
        catch (Exception ex) {
            throw new BusinessException(ErrorCode.CARD_NO_ENCRYPT_FAIL);
        }

        return paymentService.registryCard(ci, encryptedCardNo);
    }

    public List<String> findCard(String ci) {
        return paymentService.findCard(ci);
    }

    public List<Checkout> findCheckout(String ci) {
        return paymentService.findCheckout(ci);
    }
}

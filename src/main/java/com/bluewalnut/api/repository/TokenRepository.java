package com.bluewalnut.api.repository;

import com.bluewalnut.api.entity.TokenT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenT, String> {
    Boolean existsByCheckoutId(String checkoutId);
}

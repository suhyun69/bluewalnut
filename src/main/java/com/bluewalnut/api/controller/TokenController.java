package com.bluewalnut.api.controller;

import com.bluewalnut.api.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/token/v1/")
@Tag(name = "Token", description = "Token API Document")
public class TokenController {

    private final TokenService tokenService;

    @GetMapping("/publish")
    @Operation(summary = "결제 등록 요청", description = "토큰 발행")
    public String publishToken(String checkoutId) {
        return tokenService.publish(checkoutId);
    }
}

package com.bluewalnut.api.controller.token;

import com.bluewalnut.api.controller.token.dto.RequestTokenResponse;
import com.bluewalnut.api.controller.token.dto.VerifyTokenResponse;
import com.bluewalnut.api.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/token")
@Tag(name = "Token", description = "Token API Document")
public class TokenController {

    private final TokenService tokenService;

    @GetMapping("/issue")
    @Operation(summary = "Request Token", description = "토큰 발행")
    public ResponseEntity<RequestTokenResponse> RequestToken(String checkoutId) {
        String token = tokenService.requestToken(checkoutId);
        RequestTokenResponse dto = new RequestTokenResponse(token);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/verify")
    @Operation(summary = "Validate Token", description = "토큰 유효성 체크")
    public ResponseEntity<VerifyTokenResponse> VerifyToken(String token) {
        Boolean isValid = tokenService.verifyToken(token);
        VerifyTokenResponse dto = new VerifyTokenResponse(isValid);
        return ResponseEntity.ok().body(dto);
    }
}

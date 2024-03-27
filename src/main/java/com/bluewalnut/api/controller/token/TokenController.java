package com.bluewalnut.api.controller.token;

import com.bluewalnut.api.controller.token.dto.PublishTokenResponseDto;
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
@RequestMapping("/token/v1/")
@Tag(name = "PublishTokenResponseDto", description = "PublishTokenResponseDto API Document")
public class TokenController {

    private final TokenService tokenService;

    @GetMapping("/publish")
    @Operation(summary = "결제 등록 요청", description = "토큰 발행")
    public ResponseEntity<PublishTokenResponseDto> publishToken(String checkoutId) {
        String token = tokenService.publish(checkoutId);
        PublishTokenResponseDto dto = new PublishTokenResponseDto(token);
        return ResponseEntity.ok().body(dto);
    }
}

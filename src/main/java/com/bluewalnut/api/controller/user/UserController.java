package com.bluewalnut.api.controller.user;

import com.bluewalnut.api.controller.user.dto.EnrollCardRequest;
import com.bluewalnut.api.controller.user.dto.EnrollCardResponse;
import com.bluewalnut.api.controller.user.dto.FindCardResponse;
import com.bluewalnut.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
@Tag(name = "User", description = "User API Document")
public class UserController {

    private final UserService userService;

    @PostMapping("/card")
    @Operation(summary = "카드 등록", description = "RefCardId 발행")
    public ResponseEntity<EnrollCardResponse> registryCard(@Valid @RequestBody EnrollCardRequest request) {
        String refCardId = userService.registryCard(request.getCi(), request.getCardNo());
        EnrollCardResponse response = new EnrollCardResponse(refCardId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/card")
    @Operation(summary = "내 카드 조회", description = "RefCardId 전체 조회")
    public ResponseEntity<FindCardResponse> findCard(String ci) {
        List<String> cardList = userService.findCard(ci);
        FindCardResponse response = new FindCardResponse(cardList);
        return ResponseEntity.ok().body(response);
    }
}

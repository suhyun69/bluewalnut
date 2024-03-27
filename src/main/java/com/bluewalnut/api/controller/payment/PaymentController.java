package com.bluewalnut.api.controller.payment;

import com.bluewalnut.api.controller.payment.dto.ApprovalTokenResponse;
import com.bluewalnut.api.controller.payment.dto.FindStatusResponse;
import com.bluewalnut.api.controller.payment.dto.PayByCardRefIdRequest;
import com.bluewalnut.api.controller.payment.dto.PayByCardRefIdResponse;
import com.bluewalnut.api.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payment/")
@Tag(name = "Payment", description = "Payment API Document")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/checkout")
    @Operation(summary = "Pay by Card_Ref_ID", description = "checkoutId 생성")
    public ResponseEntity<PayByCardRefIdResponse> payByCardRefId(@RequestBody PayByCardRefIdRequest request) {
        String checkoutId = paymentService.payByCardRefId(request.getCi(), request.getCardRefId(), request.getAmount());
        PayByCardRefIdResponse response = new PayByCardRefIdResponse(checkoutId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/checkout")
    @Operation(summary = "checkout status 조회", description = "checkout status 조회")
    public ResponseEntity<FindStatusResponse> findStatus(String checkoutId) {
        String status = paymentService.findStatus(checkoutId);
        FindStatusResponse response = new FindStatusResponse(status);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/approval")
    @Operation(summary = "ApprovalToken", description = "PG사 결제 요청")
    public ResponseEntity<ApprovalTokenResponse> approvalToken(String token) {
        String checkoutId = paymentService.approvalToken(token);
        ApprovalTokenResponse response = new ApprovalTokenResponse(checkoutId);
        return ResponseEntity.ok().body(response);
    }
}

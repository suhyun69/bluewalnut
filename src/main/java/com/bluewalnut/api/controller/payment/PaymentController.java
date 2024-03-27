package com.bluewalnut.api.controller.payment;

import com.bluewalnut.api.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment/v1/")
@Tag(name = "Payment", description = "Payment API Document")
public class PaymentController {

    private final PaymentService paymentService;


}

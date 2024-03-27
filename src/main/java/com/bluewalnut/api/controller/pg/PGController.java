package com.bluewalnut.api.controller.pg;

import com.bluewalnut.api.controller.user.dto.EnrollCardRequest;
import com.bluewalnut.api.controller.user.dto.EnrollCardResponse;
import com.bluewalnut.api.service.PGService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pg/v1/")
@Tag(name = "PG", description = "PG API Document")
public class PGController {

    private final PGService pgService;




}

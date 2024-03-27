package com.bluewalnut.api.controller.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollCardRequest {
    public String ci;

    @NotNull(message = "cardNo은(는) null일 수 없습니다.")
    @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}-\\d{4}", message = "cardNo은(는) 1234-5678-9012-3456 형식으로 입력하세요.")
    public String cardNo;
}

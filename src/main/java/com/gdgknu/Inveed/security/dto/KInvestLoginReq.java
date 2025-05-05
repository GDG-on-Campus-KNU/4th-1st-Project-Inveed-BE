package com.gdgknu.Inveed.security.dto;

import jakarta.validation.constraints.NotBlank;

public record KInvestLoginReq(
        @NotBlank(message = "한국투자증권 앱키는 필수 입력값입니다.")
        String appKey,
        @NotBlank(message = "한국투자증권 앱시크릿은 필수 입력값입니다.")
        String appSecret
){
}
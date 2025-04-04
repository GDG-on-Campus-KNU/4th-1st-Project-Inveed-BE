package com.gdgknu.Inveed.domain.kinvest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record KInvestTradeReqDTO (
        @NotNull(message = "한국투자증권 엑세스 토큰은 필수 입력값입니다.")
        String kInvestAccessToken,
        @NotBlank(message = "한국투자증권 앱키는 필수 입력값입니다.")
        String appKey,
        @NotBlank(message = "한국투자증권 앱시크릿은 필수 입력값입니다.")
        String appSecret
){
}

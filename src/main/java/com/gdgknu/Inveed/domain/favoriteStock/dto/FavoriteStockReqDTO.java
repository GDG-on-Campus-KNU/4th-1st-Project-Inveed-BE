package com.gdgknu.Inveed.domain.favoriteStock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FavoriteStockReqDTO(
        @NotBlank(message = "종목 코드는 필수 입력값입니다.")
        String symb
) {
}

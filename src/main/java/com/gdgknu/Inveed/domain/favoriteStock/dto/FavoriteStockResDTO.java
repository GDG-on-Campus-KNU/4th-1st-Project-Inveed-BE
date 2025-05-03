package com.gdgknu.Inveed.domain.favoriteStock.dto;

import com.gdgknu.Inveed.domain.favoriteStock.entity.FavoriteStock;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FavoriteStockResDTO(
        @NotBlank(message = "종목 코드는 필수 입력값입니다.")
        String symb,
        @NotBlank(message = "생성 일시는 필수 입력값입니다.")
        LocalDateTime createdDate
) {
    public static FavoriteStockResDTO from(FavoriteStock favoriteStock) {
        return FavoriteStockResDTO.builder()
                .symb(favoriteStock.getStockCode())
                .createdDate(favoriteStock.getCreatedDate())
                .build();
    }
}

package com.gdgknu.Inveed.domain.stockRank.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record StockRankResDTO(
        @NotBlank(message = "종목 코드는 필수 입력값입니다.")
        String symb,
        @NotBlank(message = "종목명은 필수 입력값입니다.")
        String name,
        @NotBlank(message = "현재가는 필수 입력값입니다.")
        String last,
        @NotBlank(message = "대비는 필수 입력값입니다.")
        String diff,
        @NotBlank(message = "등략률은 필수 입력값입니다.")
        String rate,
        @NotBlank(message = "순위는 필수 입력값입니다.")
        String rank
        ) {
    public static StockRankResDTO fromJsonNode(JsonNode stockNode) {
        return StockRankResDTO.builder()
                .symb(stockNode.path("symb").asText(null))
                .name(stockNode.path("name").asText(null))
                .last(stockNode.path("last").asText(null))
                .diff(stockNode.path("diff").asText(null))
                .rate(stockNode.path("rate").asText(null))
                .rank(stockNode.path("rank").asText(null))
                .build();
    }
}
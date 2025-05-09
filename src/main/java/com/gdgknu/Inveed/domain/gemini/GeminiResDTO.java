package com.gdgknu.Inveed.domain.gemini;

import lombok.Builder;

@Builder
public record GeminiResDTO(String response) {

    public static GeminiResDTO fromResponse(String response) {
        return GeminiResDTO.builder()
                .response(response)
                .build();
    }
}

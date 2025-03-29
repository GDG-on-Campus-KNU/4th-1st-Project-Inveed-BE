package com.gdgknu.Inveed.security.dto;

import com.gdgknu.Inveed.domain.user.User;
import lombok.Builder;

@Builder
public record LoginResDTO (
    String name,
    String email,
    String accessToken,
    String refreshToken
) {
    public static LoginResDTO fromEntity(
            User user,
            String accessToken,
            String refreshToken) {
        return LoginResDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

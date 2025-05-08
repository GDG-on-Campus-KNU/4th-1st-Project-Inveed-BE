package com.gdgknu.Inveed.security.service;

import com.gdgknu.Inveed.response.CustomException;
import com.gdgknu.Inveed.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    public void saveRefreshToken(String email, String refreshToken) {
        redisTemplate.opsForValue().set(email, refreshToken, 21, TimeUnit.DAYS);
    }

    public String getRefreshToken(String email) {
        String existingRefreshToken = redisTemplate.opsForValue().get(email);
        if(existingRefreshToken == null) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }
        return existingRefreshToken;
    }

    public void deleteTokens(String email) {
        redisTemplate.delete(email);
    }

}

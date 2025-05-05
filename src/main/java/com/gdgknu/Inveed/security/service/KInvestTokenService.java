package com.gdgknu.Inveed.security.service;

import com.gdgknu.Inveed.response.CustomException;
import com.gdgknu.Inveed.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class KInvestTokenService {
    private final RedisTemplate<String, String> redisTemplate;
    private final String KEY_PREFIX = "KINVEST";

    public void saveKInvestToken(String key, String kInvestToken) {
        redisTemplate.opsForValue().set(KEY_PREFIX + key, kInvestToken, 1, TimeUnit.DAYS);
    }

    public String getKInvestToken(String key) {
        String existingToken = redisTemplate.opsForValue().get(KEY_PREFIX + key);
        if(existingToken == null) {
            throw new CustomException(ErrorCode.KINVEST_TOKEN_NOT_FOUND);
        }
        return existingToken;
    }

    public void deleteTokens(String key) {
        redisTemplate.delete(KEY_PREFIX + key);
    }
}

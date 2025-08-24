package com.expense.api.security.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class JwtBlacklistService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String BLACKLIST_PREFIX = "blacklist:";

    public void blacklistToken(String token, long expirationMillis) {
        String key = BLACKLIST_PREFIX + token;
        redisTemplate.opsForValue().set(key, true, expirationMillis, TimeUnit.MILLISECONDS);
    }

    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.hasKey(BLACKLIST_PREFIX + token);
    }
}

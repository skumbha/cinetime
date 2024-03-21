package com.cinetime.theater.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean isCacheUnavailable() {
        // Logic to check if Redis cache is unavailable (e.g., Redis connection status check)
        return !isRedisAvailable();
    }

    private boolean isRedisAvailable() {
        // Logic to check Redis availability using the autowired RedisTemplate
        try {
            redisTemplate.getConnectionFactory().getConnection().ping();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
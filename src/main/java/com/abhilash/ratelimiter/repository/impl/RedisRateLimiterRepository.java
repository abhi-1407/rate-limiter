package com.abhilash.ratelimiter.repository.impl;

import com.abhilash.ratelimiter.repository.RateLimiterRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

import java.util.Collections;

@Repository
public class RedisRateLimiterRepository
        implements RateLimiterRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private final RedisScript<Long> slidingWindowScript;

    public RedisRateLimiterRepository(
            RedisTemplate<String, String> redisTemplate,
            RedisScript<Long> slidingWindowScript
    ) {
        this.redisTemplate = redisTemplate;
        this.slidingWindowScript = slidingWindowScript;
    }

    @Override
    public boolean allowRequest(
            String key,
            long currentTime,
            long windowSize,
            long limit,
            String requestId
    ) {

        Long result =
                redisTemplate.execute(
                        slidingWindowScript,
                        Collections.singletonList(key),
                        String.valueOf(currentTime),
                        String.valueOf(windowSize),
                        String.valueOf(limit),
                        requestId
                );

        return result != null && result == 1;
    }
}
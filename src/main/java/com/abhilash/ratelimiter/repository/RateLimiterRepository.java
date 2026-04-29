package com.abhilash.ratelimiter.repository;

public interface RateLimiterRepository {

    boolean allowRequest(
            String key,
            long currentTime,
            long windowSize,
            long limit,
            String requestId
    );
}
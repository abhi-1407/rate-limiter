package com.abhilash.ratelimiter.dto;

import jakarta.validation.constraints.NotBlank;

public class RateLimitRequest {

    @NotBlank(message = "User Id is required")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(
            String userId
    ) {
        this.userId = userId;
    }
}
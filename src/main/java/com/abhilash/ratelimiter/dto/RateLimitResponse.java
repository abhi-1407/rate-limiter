package com.abhilash.ratelimiter.dto;

public class RateLimitResponse {

    private boolean allowed;

    public RateLimitResponse() {
    }

    public RateLimitResponse(boolean allowed) {
        this.allowed = allowed;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }
}
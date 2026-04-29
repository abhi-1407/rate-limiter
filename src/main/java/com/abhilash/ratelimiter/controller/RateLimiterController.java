package com.abhilash.ratelimiter.controller;

import com.abhilash.ratelimiter.dto.RateLimitRequest;
import com.abhilash.ratelimiter.dto.RateLimitResponse;
import com.abhilash.ratelimiter.service.RateLimiterService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RateLimiterController {

    private final RateLimiterService service;

    public RateLimiterController(
            RateLimiterService service
    ) {
        this.service = service;
    }

    @PostMapping("/rate-limit")
    public RateLimitResponse checkRateLimit(
            @Valid
            @RequestBody
            RateLimitRequest request
    ) {

        service.validateRequest(
                request.getUserId()
        );

        return new RateLimitResponse(
                true,
                "Request Allowed"
        );
    }
}
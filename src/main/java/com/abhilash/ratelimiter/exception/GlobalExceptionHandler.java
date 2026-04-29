package com.abhilash.authsystem.exception;

import com.abhilash.ratelimiter.exception.RateLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            RateLimitExceededException.class
    )
    public ResponseEntity<?> handleRateLimitExceeded(
            RateLimitExceededException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body(
                        Map.of(
                                "allowed", false,
                                "error", ex.getMessage()
                        )
                );
    }
}
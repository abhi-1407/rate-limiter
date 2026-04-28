package com.abhilash.authsystem.controller;

import com.abhilash.authsystem.dto.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class TestController {

    @GetMapping("/hello")
    public ResponseEntity<?> isAuthenticated(){
        return ResponseEntity.ok("Hello after authentication");
    }
}

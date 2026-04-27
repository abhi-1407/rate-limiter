package com.abhilash.authsystem.controller;

import com.abhilash.authsystem.dto.LoginRequest;
import com.abhilash.authsystem.dto.RegisterRequest;
import com.abhilash.authsystem.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        String response = authService.register(request);
        if(response.equals("Already exists")){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User Already Exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("User Created");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        String response = authService.login(request);

        if(response.equals("User doesn't exist")){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Incorrect Email ID");
        }else if(response.equals("Incorrect credentials")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect Password");
        }
        return ResponseEntity.ok(Map.of("token",response));
    }
}

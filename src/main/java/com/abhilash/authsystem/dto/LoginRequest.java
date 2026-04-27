package com.abhilash.authsystem.dto;


import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}

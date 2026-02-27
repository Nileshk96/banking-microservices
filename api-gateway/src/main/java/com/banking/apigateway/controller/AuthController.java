package com.banking.apigateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.banking.apigateway.security.JwtUtil;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        if ("admin".equals(username) && "admin123".equals(password)) {
            return jwtUtil.generateToken(username, "ADMIN");
        }

        if ("user".equals(username) && "user123".equals(password)) {
            return jwtUtil.generateToken(username, "USER");
        }

        throw new RuntimeException("Invalid credentials");
    }
}
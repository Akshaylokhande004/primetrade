package com.primetrade.primetrade_api.controller;

import com.primetrade.primetrade_api.dto.AuthResponse;
import com.primetrade.primetrade_api.dto.LoginRequest;
import com.primetrade.primetrade_api.dto.RegisterRequest;
import com.primetrade.primetrade_api.model.User;
import com.primetrade.primetrade_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest)
    {
        authService.register(registerRequest);
        return ResponseEntity.ok("User Registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }



}

package com.aryil.productapi.controller;

import com.aryil.productapi.dto.request.AuthRequest;
import com.aryil.productapi.dto.request.RegisterRequest;
import com.aryil.productapi.dto.response.AuthResponse;
import com.aryil.productapi.dto.response.ApiResponse;
import com.aryil.productapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.ok(new ApiResponse<>(true, "Kayıt başarılı", null));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}

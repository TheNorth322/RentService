package com.example.rentservice.controller;

import com.example.rentservice.dto.auth.AuthenticationRequest;
import com.example.rentservice.dto.auth.AuthenticationResponse;
import com.example.rentservice.dto.auth.RegisterRequest;
import com.example.rentservice.entity.auth.RefreshToken;
import com.example.rentservice.exception.auth.RefreshTokenNotFoundException;
import com.example.rentservice.service.AuthenticationService;
import com.example.rentservice.service.JwtService;
import com.example.rentservice.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(authService.register(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(authService.authenticate(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/forgot/password")
    public ResponseEntity forgotPassword(@RequestParam String email) {
        try {
            return ResponseEntity.ok(authService.forgotPassword(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/validate/password/token")
    public ResponseEntity validatePasswordResetToken(@RequestParam String token) {
        try {
            return ResponseEntity.ok(authService.validatePasswordResetToken(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset/password")
    public ResponseEntity resetPassword(@RequestParam String password, @RequestParam String token) {
        try {
            return ResponseEntity.ok(authService.resetPassword(password, token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify/email")
    public ResponseEntity sendVerificationEmail(@RequestParam String email) {
        try {
            return ResponseEntity.ok(authService.sendVerificationEmail(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/validate/email/token")
    public ResponseEntity validateEmailVerificationToken(@RequestParam String token) {
        try {
            return ResponseEntity.ok(authService.validateEmailVerificationToken(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public AuthenticationResponse refreshToken(@RequestParam String token) throws RefreshTokenNotFoundException {
        return refreshTokenService.findByToken(token)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user);
                    return AuthenticationResponse
                            .builder()
                            .accessToken(accessToken)
                            .refreshToken(token)
                            .build();
                }).orElseThrow(() -> new RefreshTokenNotFoundException("Refresh token not found"));
    }
}

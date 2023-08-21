package com.madmin.policies.controller;

import com.madmin.policies.services.AuthenticationService;
import com.madmin.policies.utils.ErrorResponse;
import com.madmin.policies.utils.LoginRequest;
import com.madmin.policies.utils.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.madmin.policies.exceptions.AuthenticationException;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authService;
    @GetMapping("/main")
    public String showLoginPage() {
        return "redirect:/login.html"; // Returnează numele fișierului HTML (fără extensie) din folderul resources/static
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            String token = authService.login(username, password);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}
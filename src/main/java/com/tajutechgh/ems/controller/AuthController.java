package com.tajutechgh.ems.controller;

import com.tajutechgh.ems.dto.RegisterDto;
import com.tajutechgh.ems.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // TODO: register user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {

        String response = authService.registerUser(registerDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

package com.chukwuemeka.simple_store.controllers;

import com.chukwuemeka.simple_store.dtos.LoginDto;
import com.chukwuemeka.simple_store.dtos.LoginResponse;
import com.chukwuemeka.simple_store.dtos.SignUpDto;
import com.chukwuemeka.simple_store.payloads.ApiResponse;
import com.chukwuemeka.simple_store.payloads.SignupResponse;
import com.chukwuemeka.simple_store.repositories.UserRepository;
import com.chukwuemeka.simple_store.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from frontend
public class AuthController {
    private final UserRepository userRepository;

    private final AuthService authService;
    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse<SignupResponse>> addUser(@RequestBody @Validated SignUpDto signUpDto){
        return authService.addUser(signUpDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@RequestBody LoginDto loginDto){
        return authService.loginUser(loginDto);
    }

}

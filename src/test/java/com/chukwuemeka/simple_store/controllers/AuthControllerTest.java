package com.chukwuemeka.simple_store.controllers;

import com.chukwuemeka.simple_store.dtos.LoginDto;
import com.chukwuemeka.simple_store.dtos.LoginResponse;
import com.chukwuemeka.simple_store.dtos.SignUpDto;
import com.chukwuemeka.simple_store.payloads.ApiResponse;
import com.chukwuemeka.simple_store.payloads.SignupResponse;
import com.chukwuemeka.simple_store.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private SignUpDto signUpDto;
    private LoginDto loginDto;
    private SignupResponse signupResponse;
    private LoginResponse loginResponse;

    @BeforeEach
    public void setUp() {
        signUpDto = new SignUpDto("John", "Doe", "john.doe@example.com", "password");
        loginDto = new LoginDto("john.doe@example.com", "password");
        signupResponse = new SignupResponse("John", "Doe", "john.doe@example.com");
        loginResponse = new LoginResponse("john.doe@example.com");
    }

    @Test
    public void testAddUser_Success() {
        ApiResponse<SignupResponse> apiResponse = new ApiResponse<>("successful", signupResponse);
        when(authService.addUser(any(SignUpDto.class))).thenReturn(ResponseEntity.ok(apiResponse));

        ResponseEntity<ApiResponse<SignupResponse>> response = authController.addUser(signUpDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("successful", response.getBody().getMessage());
        assertEquals(signupResponse, response.getBody().getData());
        verify(authService, times(1)).addUser(any(SignUpDto.class));
    }

    @Test
    public void testLoginUser_Success() {
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>("Login successful", loginResponse);
        when(authService.loginUser(any(LoginDto.class))).thenReturn(ResponseEntity.ok(apiResponse));

        ResponseEntity<ApiResponse<LoginResponse>> response = authController.loginUser(loginDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful", response.getBody().getMessage());
        assertEquals(loginResponse, response.getBody().getData());
        verify(authService, times(1)).loginUser(any(LoginDto.class));
    }
}

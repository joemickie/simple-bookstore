package com.chukwuemeka.simple_store.services;

import com.chukwuemeka.simple_store.dtos.LoginDto;
import com.chukwuemeka.simple_store.dtos.LoginResponse;
import com.chukwuemeka.simple_store.dtos.SignUpDto;
import com.chukwuemeka.simple_store.payloads.ApiResponse;
import com.chukwuemeka.simple_store.payloads.SignupResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<ApiResponse<SignupResponse>> addUser (SignUpDto signUpDto);
    ResponseEntity<ApiResponse<LoginResponse>> loginUser (LoginDto loginDto);

}

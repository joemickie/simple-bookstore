package com.chukwuemeka.simple_store.services.serviceImplementations;

import com.chukwuemeka.simple_store.dtos.LoginDto;
import com.chukwuemeka.simple_store.dtos.LoginResponse;
import com.chukwuemeka.simple_store.dtos.SignUpDto;
import com.chukwuemeka.simple_store.exceptions.SimpleBookstoreException;
import com.chukwuemeka.simple_store.models.User;
import com.chukwuemeka.simple_store.payloads.ApiResponse;
import com.chukwuemeka.simple_store.payloads.SignupResponse;
import com.chukwuemeka.simple_store.repositories.UserRepository;
import com.chukwuemeka.simple_store.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;
    @Override
    public ResponseEntity<ApiResponse<SignupResponse>> addUser(SignUpDto signUpDto) {
        var user = userRepository.findByEmail(signUpDto.email());
        if (user.isPresent()) {
            throw new SimpleBookstoreException("Email already exist");
        }
        else {
            User newUser = new User();
            newUser.setFirstName(signUpDto.firstName());
            newUser.setLastName(signUpDto.lastName());
            newUser.setEmail(signUpDto.email());
            newUser.setPassword(signUpDto.password());

            var savedUser = userRepository.save(newUser);

            SignupResponse signupResponse = new SignupResponse(signUpDto.firstName(),signUpDto.lastName(),signUpDto.email());
            ApiResponse<SignupResponse> response = new ApiResponse<>("successful",signupResponse);
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(LoginDto loginDto) {

        User user = userRepository.findByEmailAndPassword(loginDto.email(), loginDto.password()).orElseThrow(() -> new SimpleBookstoreException("Check email and password"));
        if (user.getEmail().equals(loginDto.email()) & user.getPassword().equals(loginDto.password())){
                LoginResponse loginResponse = new LoginResponse(loginDto.email());
                ApiResponse<LoginResponse> response = new ApiResponse<>("Login successful",loginResponse);
                return new ResponseEntity<>(response, response.getStatus());
        }
        throw new SimpleBookstoreException("User not found");
    }
}

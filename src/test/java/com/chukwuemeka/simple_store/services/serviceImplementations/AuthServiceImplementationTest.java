package com.chukwuemeka.simple_store.services.serviceImplementations;

import com.chukwuemeka.simple_store.dtos.LoginDto;
import com.chukwuemeka.simple_store.dtos.LoginResponse;
import com.chukwuemeka.simple_store.dtos.SignUpDto;
import com.chukwuemeka.simple_store.exceptions.SimpleBookstoreException;
import com.chukwuemeka.simple_store.models.User;
import com.chukwuemeka.simple_store.payloads.ApiResponse;
import com.chukwuemeka.simple_store.payloads.SignupResponse;
import com.chukwuemeka.simple_store.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplementationTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImplementation authService;

    private SignUpDto signUpDto;
    private LoginDto loginDto;
    private User user;

    @BeforeEach
    public void setUp() {
        signUpDto = new SignUpDto("John", "Doe", "john.doe@example.com", "password");
        loginDto = new LoginDto("john.doe@example.com", "password");
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
    }

    @Test
    public void testAddUser_Success() {
        when(userRepository.findByEmail(signUpDto.email())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<ApiResponse<SignupResponse>> response = authService.addUser(signUpDto);

        assertEquals("successful", response.getBody().getMessage());
        assertEquals(signUpDto.email(), response.getBody().getData().email());
        verify(userRepository, times(1)).findByEmail(signUpDto.email());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testAddUser_EmailAlreadyExists() {
        when(userRepository.findByEmail(signUpDto.email())).thenReturn(Optional.of(user));

        assertThrows(SimpleBookstoreException.class, () -> authService.addUser(signUpDto));

        verify(userRepository, times(1)).findByEmail(signUpDto.email());
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testLoginUser_Success() {
        when(userRepository.findByEmailAndPassword(loginDto.email(), loginDto.password())).thenReturn(Optional.of(user));

        ResponseEntity<ApiResponse<LoginResponse>> response = authService.loginUser(loginDto);

        assertEquals("Login successful", response.getBody().getMessage());
        assertEquals(loginDto.email(), response.getBody().getData().email());
        verify(userRepository, times(1)).findByEmailAndPassword(loginDto.email(), loginDto.password());
    }

    @Test
    public void testLoginUser_InvalidCredentials() {
        when(userRepository.findByEmailAndPassword(loginDto.email(), loginDto.password())).thenReturn(Optional.empty());

        assertThrows(SimpleBookstoreException.class, () -> authService.loginUser(loginDto));

        verify(userRepository, times(1)).findByEmailAndPassword(loginDto.email(), loginDto.password());
    }
}

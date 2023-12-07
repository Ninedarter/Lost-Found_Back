package com.example.LostAndFoundApp.auth;

import com.example.LostAndFoundApp.config.JwtService;
import com.example.LostAndFoundApp.token.TokenRepository;
import com.example.LostAndFoundApp.user.Gender;
import com.example.LostAndFoundApp.user.Role;
import com.example.LostAndFoundApp.user.User;
import com.example.LostAndFoundApp.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testRegister() {
//
//        RegisterRequest request = RegisterRequest.builder()
//                .firstname("John")
//                .lastname("Doe")
//                .email("john.doe@example.com")
//                .password("password123")
//                .gender(Gender.MALE)
//                .dob("1990-01-01")
//                .role(Role.USER)
//                .phoneNumber("1234567890")
//                .build();
//
//        User user = User.builder()
//                .firstname(request.getFirstname())
//                .lastname(request.getLastname())
//                .email(request.getEmail())
//                .dob(LocalDate.parse(request.getDob()))
//                .gender(request.getGender())
//                .phoneNumber(request.getPhoneNumber())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.USER)
//                .build();
//
//        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
//        when(userRepository.save(any(User.class))).thenReturn(user);
//        when(jwtService.generateToken(any(User.class))).thenReturn("mockedAccessToken");
//        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("mockedRefreshToken");
//
//        AuthenticationResponse response = authenticationService.register(request);
//
//        Assertions.assertEquals("mockedAccessToken", response.getAccessToken());
//        Assertions.assertEquals("mockedRefreshToken", response.getRefreshToken());
//        verify(tokenRepository, times(1)).save(any());
//    }




}

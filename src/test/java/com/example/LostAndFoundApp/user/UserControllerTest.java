package com.example.LostAndFoundApp.user;

import com.example.LostAndFoundApp.item.found.SampleTestObjects;
import com.example.LostAndFoundApp.report.ReportUserRequest;
import com.example.LostAndFoundApp.report.ReportUserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = SampleTestObjects.createUser();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getById_UserExists_ReturnsOk() {
        Long userId = 1L;
        when(userService.getById(userId)).thenReturn(new ResponseEntity<>(sampleUser, HttpStatus.OK));

        ResponseEntity<User> responseEntity = userController.getById(userId);

        verify(userService, times(1)).getById(userId);
        Assertions.assertSame(sampleUser, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getById_UserNotFound_ReturnsNotFound() {
        Long userId = 2L;
        when(userService.getById(userId)).thenReturn(new ResponseEntity<>(new User(), HttpStatus.NOT_FOUND));

        ResponseEntity<User> responseEntity = userController.getById(userId);

        verify(userService, times(1)).getById(userId);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void changePassword_ValidRequest_ReturnsOk() {
        ChangePasswordRequest request = SampleTestObjects.createChangePasswordRequest();
        Principal principal = mock(Principal.class);

        doNothing().when(userService).changePassword(request, principal);

        ResponseEntity<?> responseEntity = userController.changePassword(request, principal);

        verify(userService, times(1)).changePassword(request, principal);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void reportUser_ValidRequest_ReturnsOk() {
        ReportUserRequest request = SampleTestObjects.createReportUserRequest();

        Authentication mockAuthentication = mock(Authentication.class);
        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);
        when(mockUserDetails.isCredentialsNonExpired()).thenReturn(true);

        when(userService.reportUser(request, mockAuthentication)).thenReturn(new ReportUserResponse(true));

        ResponseEntity<?> responseEntity = userController.reportUser(request, mockAuthentication);

        verify(userService, times(1)).reportUser(request, mockAuthentication);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getSelfInfo_UserExists_ReturnsOk() {
        Principal principal = mock(Principal.class);
        when(userService.getSelf(principal)).thenReturn(new ResponseEntity<>(sampleUser, HttpStatus.OK));

        ResponseEntity<User> responseEntity = userController.getSelfInfo(principal);

        verify(userService, times(1)).getSelf(principal);
        Assertions.assertSame(sampleUser, responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void banUser_ValidUserId_ReturnsOk() {
        Long userId = 1L;
        doNothing().when(userService).banUser(userId);

        ResponseEntity<?> responseEntity = userController.banUser(userId);

        verify(userService, times(1)).banUser(userId);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }



}

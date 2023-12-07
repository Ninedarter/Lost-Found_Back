package com.example.LostAndFoundApp.user;

import com.example.LostAndFoundApp.item.found.SampleTestObjects;
import com.example.LostAndFoundApp.report.ReportUserRequest;
import com.example.LostAndFoundApp.report.ReportUserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
        when(userService.reportUser(request)).thenReturn(new ReportUserResponse(true));

        ResponseEntity<?> responseEntity = userController.reportUser(request);

        verify(userService, times(1)).reportUser(request);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


}

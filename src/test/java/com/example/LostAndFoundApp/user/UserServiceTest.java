package com.example.LostAndFoundApp.user;

import com.example.LostAndFoundApp.mapping.MappingService;
import com.example.LostAndFoundApp.report.Report;
import com.example.LostAndFoundApp.report.ReportRepository;
import com.example.LostAndFoundApp.report.ReportUserRequest;
import com.example.LostAndFoundApp.report.ReportUserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private MappingService mappingService;

    @InjectMocks
    private UserService userService;

    private User createUser() {
        return User.builder()
                .id(1)
                .firstname("John")
                .lastname("Doe")
                .email("test@example.com")
                .phoneNumber("123456789")
                .dob(LocalDate.of(2000, 1, 1))
                .gender(Gender.MALE)
                .password("oldPassword")
                .role(Role.USER)
                .build();
    }

    @Test
    @DisplayName("Changed password, then return changed password")
    void testChangePasswordSuccess() {
        ChangePasswordRequest request = new ChangePasswordRequest("oldPassword", "newPassword", "newPassword");
        User user = createUser();

        // Create a mock for UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authentication = mock(UsernamePasswordAuthenticationToken.class);

        when(passwordEncoder.matches("oldPassword", "oldPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        // change password
        userService.changePassword(request, new UsernamePasswordAuthenticationToken(user, null));

        // Capture the argument after the save method is called
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        // Retrieve the captured User object
        User savedUser = userCaptor.getValue();

        // Assertions to check if the password was changed
        Assertions.assertEquals("encodedNewPassword", savedUser.getPassword());
    }

    @Test
    @DisplayName("Change password with wrong current password: get exception")
    void testChangePasswordWrongPassword() {
        ChangePasswordRequest request = new ChangePasswordRequest("wrongCurrentPassword", "newPassword", "newPassword");
        User user = createUser();

        UsernamePasswordAuthenticationToken authentication = mock(UsernamePasswordAuthenticationToken.class);
        when(passwordEncoder.matches("wrongCurrentPassword", user.getPassword())).thenReturn(false);

        // Assert that changing the password with the wrong current password throws an IllegalStateException
        Assertions.assertThrows(IllegalStateException.class, () -> userService.changePassword(request, new UsernamePasswordAuthenticationToken(user, null)));
    }

    @Test
    @DisplayName("Change password with not matching new password: get exception")
    void testChangePasswordNotSamePasswords() {
        ChangePasswordRequest request = new ChangePasswordRequest("oldPassword", "newPassword", "differentNewPassword");
        User user = createUser();

        UsernamePasswordAuthenticationToken authentication = mock(UsernamePasswordAuthenticationToken.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(passwordEncoder.matches("oldPassword", user.getPassword())).thenReturn(true);

        // Assert that changing the password with non-matching new passwords throws an IllegalStateException
        Assertions.assertThrows(IllegalStateException.class, () -> userService.changePassword(request, authentication));
    }

    @Test
    void getById_ExistingId_ReturnsOk() {

        Long id = 1L;
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userService.getById(id);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertSame(user, response.getBody());
    }

    @Test
    void getById_NonExistingId_ReturnsNotFound() {

        Long id = 2L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userService.getById(id);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Report user: success")
    void testReportUser_Success() {
        ReportUserRequest request = new ReportUserRequest("reportingUser@example.com", "reportedUser@example.com", "Reason for report");

        User reportedUser = createUser();
        when(userRepository.findByEmail(request.getReportedUserEmail())).thenReturn(Optional.of(reportedUser));
        when(mappingService.mapReport(request)).thenReturn(new Report());

        ReportUserResponse response = userService.reportUser(request);

        Assertions.assertTrue(response.isSuccess());
        verify(reportRepository).save(any(Report.class));
    }

    @Test
    @DisplayName("Report user: self-reporting, should fail")
    void testReportUser_SelfReporting() {
        ReportUserRequest request = new ReportUserRequest("reportingUser@example.com", "reportingUser@example.com", "Reason for report");

        when(mappingService.mapReport(request)).thenReturn(new Report());

        ReportUserResponse response = userService.reportUser(request);

        Assertions.assertFalse(response.isSuccess());
        verify(reportRepository, never()).save(any(Report.class));
    }

    @Test
    @DisplayName("Report user: user does not exist, should fail")
    void testReportUser_UserDoesNotExist() {
        ReportUserRequest request = new ReportUserRequest("reportingUser@example.com", "nonExistingUser@example.com", "Reason for report");

        when(userRepository.findByEmail(request.getReportedUserEmail())).thenReturn(Optional.empty());
        when(mappingService.mapReport(request)).thenReturn(new Report());

        ReportUserResponse response = userService.reportUser(request);

        Assertions.assertFalse(response.isSuccess());
        verify(reportRepository, never()).save(any(Report.class));
    }

    @Test
    @DisplayName("Check if user is reporting itself")
    void testIsUserReportingItself() {
        ReportUserRequest request = new ReportUserRequest("user@example.com", "user@example.com", "Reason for report");

        boolean result = userService.isUserReportingItself(request);

        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Check if user exists")
    void testDoesUserExists() {
        ReportUserRequest request = new ReportUserRequest("reportedUser@example.com", "reportingUser@example.com", "Reason for report");
        User reportedUser = createUser();

        when(userRepository.findByEmail(request.getReportedUserEmail())).thenReturn(Optional.of(reportedUser));

        boolean result = userService.doesUserExists(request);

        Assertions.assertTrue(result);
    }





}

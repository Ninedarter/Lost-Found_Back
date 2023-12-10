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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
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
                .id(1L)
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
        ReportUserRequest request = new ReportUserRequest(1L, "Reason for report");

        User reportedUser = createUser();
        reportedUser.setId(1L);

        when(userRepository.findById(request.getReportedUserId())).thenReturn(Optional.of(reportedUser));

        UserDetails mockUserDetails = new org.springframework.security.core.userdetails.User(
                "reportingUser@example.com",
                "password",
                Collections.emptyList()
        );

        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(mockUserDetails, "password");

        when(mappingService.mapReport(request, "reportingUser@example.com")).thenReturn(new Report());

        ReportUserResponse response = userService.reportUser(request, mockAuthentication);

        Assertions.assertTrue(response.isSuccess());
        verify(reportRepository).save(any(Report.class));
    }

    @Test
    @DisplayName("Check if user exists")
    void testDoesUserExists() {
        ReportUserRequest request = new ReportUserRequest(1L, "Reason for report"); // Assuming 1L is the reported user's ID
        User reportedUser = createUser();

        when(userRepository.findById(request.getReportedUserId())).thenReturn(Optional.of(reportedUser));

        boolean result = userService.doesUserExists(request);

        Assertions.assertTrue(result);
    }

    @Test
    void getSelf_UserExists_ReturnsOk() {
        UserService userService = new UserService(passwordEncoder, userRepository, mappingService, reportRepository, userRepository);
        User mockUser = new User();
        Principal mockPrincipal = new UsernamePasswordAuthenticationToken(mockUser, null);

        ResponseEntity<User> responseEntity = userService.getSelf(mockPrincipal);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertSame(mockUser, responseEntity.getBody());
    }

    @Test
    void banUser_UserExists_StatusBanned() {

        UserService userService = new UserService(passwordEncoder, userRepository, mappingService, reportRepository, userRepository);
        User mockUser = new User();
        mockUser.setId(1L);
        Optional<User> optionalUser = Optional.of(mockUser);
        when(userRepository.findById(1L)).thenReturn(optionalUser);

        userService.banUser(1L);

        verify(userRepository, times(1)).findById(1L);
        Assertions.assertEquals(Status.BANNED, mockUser.getStatus());
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    @DisplayName("Get user by email: user exists")
    void testGetByEmail_UserExists_ReturnsOk() {
        String email = "test@example.com";
        User user = createUser();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userService.getByEmail(email);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertSame(user, response.getBody());
    }

    @Test
    @DisplayName("Get user by email: user does not exist")
    void testGetByEmail_UserDoesNotExist_ReturnsNotFound() {
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userService.getByEmail(email);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Ban user by email: user exists")
    void testBanUserByEmail_UserExists_StatusBanned() {
        String email = "test@example.com";
        User user = createUser();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user)); // Use userRepository for findByEmail

        userService.banUserByEmail(email);

        Assertions.assertEquals(Status.BANNED, user.getStatus());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Ban user by email: user does not exist")
    void testBanUserByEmail_UserDoesNotExist_ReturnsNotFound() {
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertDoesNotThrow(() -> userService.banUserByEmail(email));
    }

    @Test
    @DisplayName("Check if banned: user is banned")
    void testCheckIfBanned_UserIsBanned_ReturnsTrue() {
        User user = createUser();
        user.setStatus(Status.BANNED);

        Principal mockPrincipal = new UsernamePasswordAuthenticationToken(user, null);

        ResponseEntity<Boolean> response = userService.checkIfBanned(mockPrincipal);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody());
    }

    @Test
    @DisplayName("Check if banned: user is not banned")
    void testCheckIfBanned_UserIsNotBanned_ReturnsFalse() {
        User user = createUser();
        user.setStatus(Status.DEFAULT);

        Principal mockPrincipal = new UsernamePasswordAuthenticationToken(user, null);

        ResponseEntity<Boolean> response = userService.checkIfBanned(mockPrincipal);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertFalse(response.getBody());
    }

}

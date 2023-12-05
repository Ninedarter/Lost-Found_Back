package com.example.LostAndFoundApp.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

        // Ensure that the type returned by authentication.getPrincipal() is User
        when(authentication.getPrincipal()).thenReturn(user);

        when(passwordEncoder.matches("oldPassword", "oldPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        // change password
        userService.changePassword(request, authentication);

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
        when(authentication.getPrincipal()).thenReturn(user);
        when(passwordEncoder.matches("wrongCurrentPassword", user.getPassword())).thenReturn(false);

        // Assert that changing the password with the wrong current password throws an IllegalStateException
        Assertions.assertThrows(IllegalStateException.class, () -> userService.changePassword(request, authentication));
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


}

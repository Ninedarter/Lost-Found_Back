package com.example.LostAndFoundApp.user;

import com.example.LostAndFoundApp.mapping.MappingService;
import com.example.LostAndFoundApp.report.Report;
import com.example.LostAndFoundApp.report.ReportRepository;
import com.example.LostAndFoundApp.report.ReportUserRequest;
import com.example.LostAndFoundApp.report.ReportUserResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final MappingService mappingService;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    public ResponseEntity<User> getById(Long id) {
        Optional<User> u = repository.findById(id);

        try {
            if (u.isEmpty()) {
                throw new EntityNotFoundException();
            }

            return new ResponseEntity<>(u.get(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new User(), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<User> getSelf(Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public void banUser(Long id) {
        User user = getById(id).getBody();

        assert user != null;
        user.setStatus(Status.BANNED);
        repository.save(user);
    }

    public ResponseEntity<Boolean> checkIfBanned(Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if(user.getStatus() == Status.BANNED) {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
    }

    public ReportUserResponse reportUser(ReportUserRequest request, Principal connectedUser) {
        try {
            UserDetails userDetails = (UserDetails) ((Authentication) connectedUser).getPrincipal();
            String reportingUserEmail = userDetails.getUsername();
            Optional<User> optionalUser = userRepository.findById(request.getReportedUserId());
            if (optionalUser.isPresent()) {
                User reportedUser = optionalUser.get();

                if (reportingUserEmail.equalsIgnoreCase(reportedUser.getEmail())) {
                    return new ReportUserResponse(false);
                }
            } else {
                throw new NoSuchElementException();
            }

            Report report = mappingService.mapReport(request, reportingUserEmail);
            if (doesUserExists(request)) {
                reportRepository.save(report);
                return new ReportUserResponse(true);
            }

        } catch (
                NoSuchElementException e) {
            return new ReportUserResponse(false);
        }
        return new
                ReportUserResponse(false);

    }


    boolean doesUserExists(ReportUserRequest request) {
        Optional<User> user = repository.findById(request.getReportedUserId());
        return user.isPresent();
    }



}

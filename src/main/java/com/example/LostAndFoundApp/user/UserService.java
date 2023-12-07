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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final MappingService mappingService;
    private final ReportRepository reportRepository;

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


    public ReportUserResponse reportUser(ReportUserRequest request) {
        try {
            Report report = mappingService.mapReport(request);
            if (doesUserExists(request) && !isUserReportingItself(request)) {
                reportRepository.save(report);
                return new ReportUserResponse(true);
            }

        } catch (EntityNotFoundException e) {
            return new ReportUserResponse(false);
        }
        return new ReportUserResponse(false);
    }

    boolean isUserReportingItself(ReportUserRequest request) {
        return request.getUserWhoIsReportingEmail().equalsIgnoreCase(request.getReportedUserEmail());
    }

    boolean doesUserExists(ReportUserRequest request) {
        Optional<User> user = repository.findByEmail(request.getReportedUserEmail());
        return user.isPresent();
    }


}

package com.example.LostAndFoundApp.user;

import com.example.LostAndFoundApp.report.ReportUserRequest;
import com.example.LostAndFoundApp.report.ReportUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @GetMapping("/self")
    public ResponseEntity<User> getSelfInfo(Principal connectedUser) {
        return service.getSelf(connectedUser);
    }

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/report")
    public ResponseEntity<?> reportUser(@RequestBody ReportUserRequest request, Principal connectedUser) {
        UserDetails userDetails = (UserDetails) ((Authentication) connectedUser).getPrincipal();
        String userEmail = userDetails.getUsername();
        if (!userDetails.isCredentialsNonExpired()) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        if (userEmail.equalsIgnoreCase(request.getReportedUserEmail())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ReportUserResponse response = service.reportUser(request);
        return (response.isSuccess()) ? new ResponseEntity(response, HttpStatus.OK) : new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
}

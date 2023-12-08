package com.example.LostAndFoundApp.user;

import com.example.LostAndFoundApp.report.ReportUserRequest;
import com.example.LostAndFoundApp.report.ReportUserResponse;
import com.example.LostAndFoundApp.report.ReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final ReportsService reportsService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @GetMapping("/self")
    public ResponseEntity<User> getSelfInfo(Principal connectedUser) {
        return service.getSelf(connectedUser);
    }

    @GetMapping("/amibanned")
    public ResponseEntity<Boolean> checkIfSelfIsBanned(Principal connectedUser) {
        return service.checkIfBanned(connectedUser);
    }

    @PostMapping("/ban/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> banUser(@PathVariable("id") Long id) {
        service.banUser(id);
        return ResponseEntity.ok().build();
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
        if (!userDetails.isCredentialsNonExpired()) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        ReportUserResponse response = service.reportUser(request, connectedUser);
        return (response.isSuccess()) ? new ResponseEntity(response, HttpStatus.OK) : new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/reports/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll(Principal connectedUser) {
        return new ResponseEntity<>(reportsService.getAllReports(), HttpStatus.OK);
    }


}

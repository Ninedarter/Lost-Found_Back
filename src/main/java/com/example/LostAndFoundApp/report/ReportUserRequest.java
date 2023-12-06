package com.example.LostAndFoundApp.report;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportUserRequest {
    private String reportingUserEmail;
    private String reportedUserEmail;
    private String description;


}

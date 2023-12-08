package com.example.LostAndFoundApp.report;

import lombok.Data;

import java.util.List;

@Data
public class UserReportsDTO {
    private String userEmail;
    private List<ReportInfoDTO> reports;
}
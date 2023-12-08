package com.example.LostAndFoundApp.report;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportInfoDTO {
    private Long id;
    private String description;
    private LocalDate reportTime;
    private UserInfoDTO reportee;
}
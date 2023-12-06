package com.example.LostAndFoundApp.report;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportUserRequest {

    private String userEmail;
    private String description;



}

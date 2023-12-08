package com.example.LostAndFoundApp.report;

import lombok.Data;

@Data
public class UserInfoDTO {
    private String email;
    private String username;
    private boolean accountNonLocked;
}
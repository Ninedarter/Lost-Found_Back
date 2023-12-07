package com.example.LostAndFoundApp.report;


import com.example.LostAndFoundApp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "reports")
@NoArgsConstructor
@AllArgsConstructor
public class Report {


    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne
    @JoinColumn(name = "reportedUserId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "reportingUserId")
    private User reportee;

    private String description;

    private LocalDate reportTime;


    public Report(User user, User reportee, String description, LocalDate reportTime) {
        this.user = user;
        this.reportee = reportee;
        this.description = description;
        this.reportTime = reportTime;
    }
}

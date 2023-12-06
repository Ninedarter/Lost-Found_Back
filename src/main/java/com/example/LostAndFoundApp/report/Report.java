package com.example.LostAndFoundApp.report;


import com.example.LostAndFoundApp.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "reportedUser")
    private User user;

    private String description;
}

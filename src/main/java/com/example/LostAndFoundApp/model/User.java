package com.example.LostAndFoundApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String name;
    private String surname;
    private LocalDate dob;
    private String gender;

    public User(long id, String email, String name, String surname, LocalDate dob, String gender) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.gender = gender;
    }

}

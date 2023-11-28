package com.example.LostAndFoundApp.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class User {

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

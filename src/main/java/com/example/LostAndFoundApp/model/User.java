package com.example.LostAndFoundApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String email;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    private LocalDate dob;

    @NonNull
    private Gender gender;

    public User(long id, String email, String name, String surname, LocalDate dob, Gender gender) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.gender = gender;
    }

}

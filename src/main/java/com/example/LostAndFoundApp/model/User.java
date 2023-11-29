package com.example.LostAndFoundApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FoundItem> foundItems;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LostItem> lostItems;


    public User() {
    }

    public User(long id, String email, String name, String surname, LocalDate dob, Gender gender) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.gender = gender;
    }

    public User(@NonNull String email, @NonNull String name, @NonNull String surname, @NonNull LocalDate dob, @NonNull Gender gender, List<LostItem> lostItems) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.gender = gender;

        this.lostItems = lostItems;
    }
}

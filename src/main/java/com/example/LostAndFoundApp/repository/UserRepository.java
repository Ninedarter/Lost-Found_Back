package com.example.LostAndFoundApp.repository;

import com.example.LostAndFoundApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

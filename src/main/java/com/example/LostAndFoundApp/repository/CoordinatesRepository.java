package com.example.LostAndFoundApp.repository;

import com.example.LostAndFoundApp.model.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinatesRepository extends JpaRepository<Coordinates,Long> {
}

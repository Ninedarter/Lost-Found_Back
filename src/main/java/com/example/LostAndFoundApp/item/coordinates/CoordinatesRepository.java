package com.example.LostAndFoundApp.item.coordinates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CoordinatesRepository extends JpaRepository<Coordinates,Long> {

    @Query("SELECT c.id FROM Coordinates c WHERE c.latitude = :latitude AND c.longitude = :longitude")
    Optional<Long> findIdByLatitudeAndLongitude(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude
    );
}

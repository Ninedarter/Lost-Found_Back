package com.example.LostAndFoundApp.item.found;

import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FoundItemRepository extends JpaRepository<FoundItem, Long> {



    Optional<FoundItem> findByCoordinatesId(Long coordinatesId);

}

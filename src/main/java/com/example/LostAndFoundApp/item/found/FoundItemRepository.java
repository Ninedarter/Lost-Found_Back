package com.example.LostAndFoundApp.item.found;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FoundItemRepository extends JpaRepository<FoundItem, Long> {
    List<FoundItem> findByUser_Email(String email);

    List<FoundItem> findByUser_Id(Long id);

    Optional<FoundItem> findByCoordinatesId(Long coordinatesId);


    Optional<FoundItem> findByIdAndUserEmail(Long itemId, String email);
}

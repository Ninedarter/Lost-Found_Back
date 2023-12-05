package com.example.LostAndFoundApp.item.lost;

import com.example.LostAndFoundApp.item.found.FoundItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LostItemRepository extends JpaRepository<LostItem, Long> {
    List<LostItem> findByUser_Email(String email);
    Optional<LostItem> findByIdAndUserEmail(Long itemId, String email);
}

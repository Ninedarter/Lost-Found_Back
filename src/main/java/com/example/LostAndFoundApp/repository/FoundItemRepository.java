package com.example.LostAndFoundApp.repository;

import com.example.LostAndFoundApp.model.FoundItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoundItemRepository extends JpaRepository<FoundItem, Long> {
}

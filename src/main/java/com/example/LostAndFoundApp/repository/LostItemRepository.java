package com.example.LostAndFoundApp.repository;

import com.example.LostAndFoundApp.model.LostItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostItemRepository extends JpaRepository<LostItem, Long> {
}

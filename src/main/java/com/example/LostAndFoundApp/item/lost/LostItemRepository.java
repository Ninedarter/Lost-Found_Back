package com.example.LostAndFoundApp.item.lost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LostItemRepository extends JpaRepository<LostItem, Long> {


}

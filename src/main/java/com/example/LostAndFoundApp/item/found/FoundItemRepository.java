package com.example.LostAndFoundApp.item.found;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FoundItemRepository extends JpaRepository<FoundItem, Long> {



}

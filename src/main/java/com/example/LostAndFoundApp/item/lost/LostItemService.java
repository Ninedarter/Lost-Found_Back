package com.example.LostAndFoundApp.item.lost;


import com.example.LostAndFoundApp.mapping.MappingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LostItemService {

    private final LostItemRepository lostItemRepository;
    private final MappingService mappingService;

    public List<LostItem> getAll() {
        return lostItemRepository.findAll();
    }

    public LostItem add(LostItemRequest request) {
        LostItem item = mappingService.mapLostItem(request);
        lostItemRepository.save(item);
        return item;
    }

    public LostItem getById(Long id) {
        try {
            Optional<LostItem> item = lostItemRepository.findById(id);
            if (item.isPresent()) {
                return item.get();
            } else {
                throw new LostItemException("Item not found");
            }
        } catch (LostItemException e) {
            return null;
        }
    }


    public void delete(Long id) {
        Optional<LostItem> lostItemOptional = lostItemRepository.findById(id);
        if (lostItemOptional.isPresent()) {
            lostItemRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("LostItem with ID " + id + " not found");
        }
    }

    public String update(LostItemRequest request) {
        LostItem item = mappingService.mapLostItem(request);
        try {
            Optional<LostItem> lostItemOptional = lostItemRepository.findById(request.getId());
            if (lostItemOptional.isPresent()) {
                lostItemRepository.save(item);
                return "UPDATED";
            } else {
                throw new EntityNotFoundException("LostItem with ID " + request.getId() + " not found");
            }
        } catch (EntityNotFoundException e) {
            return "BAD REQUEST";
        }
    }

}

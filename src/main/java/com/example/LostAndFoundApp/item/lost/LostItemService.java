package com.example.LostAndFoundApp.item.lost;


import com.example.LostAndFoundApp.mapping.MappingService;
import com.example.LostAndFoundApp.item.lost.response.OperationStatus;
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
        Optional<LostItem> item = lostItemRepository.findById(id);
        try {
            if (item.isEmpty()) {
                return item.get();
            } else {
                throw new EntityNotFoundException("Item not found");
            }
        } catch (LostItemException e) {
            return null;
        }
    }

    public OperationStatus delete(Long id) {
        Optional<LostItem> item = lostItemRepository.findById(id);
        try {
            if (item.isPresent()) {
                throw new EntityNotFoundException("LostItem with ID " + id + " not found");
            }
            lostItemRepository.deleteById(id);
            return new OperationStatus(true, "Deleted successfully");
        } catch (EntityNotFoundException e) {
            return new OperationStatus(false, "item not found");
        }
    }

    public OperationStatus update(LostItemRequest request) {
        Optional<LostItem> lostItemOptional = lostItemRepository.findById(request.getId());
        try {
            if (!lostItemOptional.isPresent()) {
                throw new EntityNotFoundException("LostItem with ID " + request.getId() + " not found");
            }
            LostItem item = mappingService.mapLostItem(request);
            lostItemRepository.save(item);
            return new OperationStatus(true, "Update");
        } catch (EntityNotFoundException e) {
            return new OperationStatus(false, "Cannot update");
        }
    }

    private boolean doesExists(Long id) {
        return lostItemRepository.findById(id).isPresent();
    }

}

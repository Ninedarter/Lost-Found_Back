package com.example.LostAndFoundApp.item.lost;

import com.example.LostAndFoundApp.item.coordinates.CoordinatesRepository;

import com.example.LostAndFoundApp.item.lost.request.LostItemRequest;

import com.example.LostAndFoundApp.item.lost.response.LostItemResponse;
import com.example.LostAndFoundApp.mapping.MappingService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LostItemService {

    private final LostItemRepository lostItemRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final MappingService mappingService;


    public List<LostItem> getAll() {
        return lostItemRepository.findAll();
    }


    public LostItemResponse getById(Long id) {
        Optional<LostItem> item = lostItemRepository.findById(id);
        try {
            if (!doesExists(item)) {
                throw new EntityNotFoundException();
            }
            LostItem itemById = item.get();
            return new LostItemResponse(true, itemById, "FOUND BY ID" + id);
        } catch (EntityNotFoundException e) {
            return new LostItemResponse(false, "NOT FOUND");
        }
    }


    public LostItemResponse add(LostItemRequest request) {
        if (request.getId() == null) {
            request.setId(0L);
        }
        Optional<LostItem> item = lostItemRepository.findById(request.getId());
        try {
            if (doesExists(item)) {
                throw new EntityExistsException();
            }
            LostItem mappedItem = mappingService.mapLostItem(request);
            coordinatesRepository.save(mappedItem.getCoordinates());
            lostItemRepository.save(mappedItem);
            return new LostItemResponse(true, "Created successfully");

        } catch (EntityExistsException e) {
            return new LostItemResponse(false, "Item already exists");
        }

    }


    public LostItemResponse delete(Long id) {
        Optional<LostItem> item = lostItemRepository.findById(id);
        try {
            if (!doesExists(item)) {
                throw new EntityNotFoundException("Founded item  with ID " + id + " not found");
            }
            lostItemRepository.deleteById(id);
            return new LostItemResponse(true, "Deleted successfully");
        } catch (EntityNotFoundException e) {
            return new LostItemResponse(false, "Not found");
        }
    }


    public LostItemResponse update(LostItemRequest request) {
        Optional<LostItem> item = lostItemRepository.findById(request.getId());

        try {
            if (doesExists(item)) {
                LostItem mappedItem = mappingService.mapLostItem(request);
                lostItemRepository.save(mappedItem);
                return new LostItemResponse(true);
            } else {
                throw new EntityNotFoundException("Found item with ID " + request.getId() + " not found");
            }
        } catch (EntityNotFoundException e) {
            return new LostItemResponse(false, "Item to update not found");
        }
    }


    private boolean doesExists(Optional<LostItem> item) {
        return item.isPresent();
    }


    public LostItem testFindById(Long id) {
        return lostItemRepository.findById(id).get();
    }

}

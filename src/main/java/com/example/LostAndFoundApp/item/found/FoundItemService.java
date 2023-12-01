package com.example.LostAndFoundApp.item.found;


import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.coordinates.CoordinatesRepository;
import com.example.LostAndFoundApp.item.found.response.FoundItemResponse;
import com.example.LostAndFoundApp.mapping.MappingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoundItemService {


    private final FoundItemRepository foundItemRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final MappingService mappingService;


    public List<FoundItem> getAll() {
        List<FoundItem> all = foundItemRepository.findAll();
        return foundItemRepository.findAll();
    }


    public FoundItem add(FoundItemRequest request) {
        FoundItem item = mappingService.mapFoundItem(request);
        foundItemRepository.save(item);
        Coordinates foundItemCoordinates = new Coordinates(request.getLatitude(), request.getLongitude());
        coordinatesRepository.save(foundItemCoordinates);
        return item;
    }

    public FoundItemResponse getById(Long id) {
        Optional<FoundItem> item = foundItemRepository.findById(id);
        try {
            if (!doesExists(item)) {
                throw new EntityNotFoundException();
            }
            FoundItem itemById = item.get();
            return new FoundItemResponse(itemById, "FOUND BY ID", true);
        } catch (EntityNotFoundException e) {
            return new FoundItemResponse(false, "NOT FOUND");
        }
    }

    public FoundItemResponse delete(Long id) {
        Optional<FoundItem> item = foundItemRepository.findById(id);
        try {
            if (!doesExists(item)) {
                throw new EntityNotFoundException("LostItem with ID " + id + " not found");
            }
            foundItemRepository.deleteById(id);
            return new FoundItemResponse(true, "Deleted successfully");
        } catch (EntityNotFoundException e) {
            return new FoundItemResponse(false, "Not found");
        }
    }




    public String update(FoundItemRequest request) {
        FoundItem mappedItem = mappingService.mapFoundItem(request);
        try {
            Optional<FoundItem> item = foundItemRepository.findById(request.getId());
            if (!item.isPresent()) {
                foundItemRepository.save(mappedItem);
                return "UPDATED";
            } else {
                throw new EntityNotFoundException("LostItem with ID " + request.getId() + " not found");
            }
        } catch (EntityNotFoundException e) {
            return "BAD REQUEST";
        }
    }




    public FoundItem findById(Long id) {
        return foundItemRepository.findById(id).get();
    }

    private boolean doesExists(Optional<FoundItem> item) {
        return item.isPresent();
    }

}

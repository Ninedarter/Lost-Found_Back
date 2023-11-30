package com.example.LostAndFoundApp.item.found;


import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.coordinates.CoordinatesRepository;
import com.example.LostAndFoundApp.item.lost.LostItemException;
import com.example.LostAndFoundApp.mapping.MappingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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


        String servisas = "servisas";
        return foundItemRepository.findAll();
    }

    public FoundItem add(FoundItemRequest request) {
        FoundItem item = mappingService.mapFoundItem(request);
        foundItemRepository.save(item);
        Coordinates foundItemCoordinates = new Coordinates(request.getLatitude(), request.getLongitude());
        coordinatesRepository.save(foundItemCoordinates);
        return item;
    }

    public FoundItem getById(Long id) {
        try {
            Optional<FoundItem> item = foundItemRepository.findById(id);
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
        Optional<FoundItem> lostItemOptional = foundItemRepository.findById(id);
        if (lostItemOptional.isPresent()) {
            foundItemRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("LostItem with ID " + id + " not found");
        }
    }

    public String update(FoundItemRequest request) {
        FoundItem item = mappingService.mapFoundItem(request);
        try {
            Optional<FoundItem> lostItemOptional = foundItemRepository.findById(request.getId());
            if (lostItemOptional.isPresent()) {
                foundItemRepository.save(item);
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
}

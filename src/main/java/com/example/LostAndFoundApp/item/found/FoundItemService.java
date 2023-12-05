package com.example.LostAndFoundApp.item.found;


import com.example.LostAndFoundApp.item.coordinates.CoordinatesRepository;
import com.example.LostAndFoundApp.item.found.request.FoundItemRequest;
import com.example.LostAndFoundApp.item.found.response.FoundItemResponse;
import com.example.LostAndFoundApp.mapping.MappingService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoundItemService {


    private final FoundItemRepository foundItemRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final MappingService mappingService;
    private static final Logger logger = LogManager.getLogger(FoundItemService.class);


    public List<FoundItem> getAll() {
        return foundItemRepository.findAll();

    }


    public FoundItemResponse getById(Long id) {
        Optional<FoundItem> item = foundItemRepository.findById(id);
        try {
            if (!doesExists(item)) {
                throw new EntityNotFoundException();
            }
            FoundItem itemById = item.get();
            return new FoundItemResponse(true, itemById, "FOUND BY ID " + id);
        } catch (EntityNotFoundException e) {
            return new FoundItemResponse(false, "NOT FOUND");
        }
    }


    public FoundItemResponse add(FoundItemRequest request) {
        if (null == request.getId()) {
            request.setId(0L);
        }
        Optional<FoundItem> item = foundItemRepository.findById(request.getId());
        try {
            if (doesExists(item)) {
                throw new EntityExistsException();
            }
            FoundItem mappedItem = mappingService.mapFoundItem(request);
            coordinatesRepository.save(request.getCoordinates());
            foundItemRepository.save(mappedItem);
            return new FoundItemResponse(true, "Created successfully");

        } catch (EntityExistsException e) {
            return new FoundItemResponse("Item already exists");
        }

    }


    public FoundItemResponse delete(Long id) {
        Optional<FoundItem> item = foundItemRepository.findById(id);
        try {
            if (!doesExists(item)) {
                throw new EntityNotFoundException("Founded item  with ID " + id + " not found");
            }
            foundItemRepository.deleteById(id);
            return new FoundItemResponse(true, "Deleted successfully");
        } catch (EntityNotFoundException e) {
            return new FoundItemResponse(false, "Not found");
        }
    }


    public FoundItemResponse update(FoundItemRequest request) {
        Optional<FoundItem> item = foundItemRepository.findById(request.getId());

        try {
            if (doesExists(item)) {
                FoundItem mappedItem = mappingService.mapFoundItem(request);
                foundItemRepository.save(mappedItem);
                return new FoundItemResponse(true);
            } else {
                throw new EntityNotFoundException("Found item with ID " + request.getId() + " not found");
            }
        } catch (EntityNotFoundException e) {
            return new FoundItemResponse(false, "Item to update not found");
        }
    }


    private boolean doesExists(Optional<FoundItem> item) {
        return item.isPresent();
    }

    public FoundItem testFindById(Long id) {
        return foundItemRepository.findById(id).get();
    }

    public FoundItemResponse getByCoordinates(FoundItemRequest request) {

        try {
            Double latitude = request.getCoordinates().getLatitude();
            Double longitude = request.getCoordinates().getLongitude();
            Long coordinatesId = getIdByCoordinates(latitude, longitude);
            Optional<FoundItem> item = foundItemRepository.findByCoordinatesId(coordinatesId);
            if (doesExists(item)) {
                logger.info("Received item by coordinates");
                return new FoundItemResponse(true, item.get());
            } else {
                throw new EntityNotFoundException();
            }
        } catch (EntityNotFoundException e) {
            return new FoundItemResponse(false, "Not found by coordinates");
        }
    }

    private Long getIdByCoordinates(Double latitude, Double longitude) {
        Optional<Long> idOptional = coordinatesRepository.findIdByLatitudeAndLongitude(latitude, longitude);
        try {
            if (!idOptional.isPresent()) {
                throw new EntityNotFoundException();
            }
            return idOptional.get();
        } catch (EntityNotFoundException e) {
            return 0L;
        }
    }


    //    User items CRUD
    public List<FoundItem> getAllUserFoundItems(String email) {
        return foundItemRepository.findByUser_Email(email);
    }

    public FoundItemResponse updateUserFoundItem(FoundItemRequest request) {

        List<FoundItem> foundedItems = foundItemRepository.findByUser_Email(request.getEmail());

        FoundItem mappedItem = mappingService.mapFoundItem(request);
        try {
            boolean doesBelongToUser = false;
            for (FoundItem item : foundedItems) {
                if (item.getId() == mappedItem.getId()) {
                    doesBelongToUser = true;
                    break;
                }
            }
            if (!doesBelongToUser) {
                return new FoundItemResponse(false, "Item to update does not belongs to the user");
            } else {
                coordinatesRepository.save(request.getCoordinates());
                foundItemRepository.save(mappedItem);
                return new FoundItemResponse(true, "Updated successfully");
            }

        } catch (EntityNotFoundException e) {
            new FoundItemResponse(false, "Item to update does not belongs to the user");

        }
        return new FoundItemResponse(false, "Cannot update item");
    }

    public FoundItemResponse deleteUserFoundItem(FoundItemRequest request) {

        try {
            checkDeleteRequest(request);
            if (isUserProperty(request)) {
                foundItemRepository.deleteById(request.getId());
                return new FoundItemResponse(true, "Deleted successfully");
            } else {
                return new FoundItemResponse("Item not belonging to this user");
            }
        } catch (EntityNotFoundException e) {
            return new FoundItemResponse(false, "Not found");
        }
    }

    private boolean isUserProperty(FoundItemRequest request) {
        Optional<FoundItem> byIdAndUserEmail = foundItemRepository.findByIdAndUserEmail(request.getId(), request.getEmail());
        if (!doesExists(byIdAndUserEmail)) {
            throw new EntityNotFoundException("Not found");
        }
        return true;
    }

    private void checkDeleteRequest(FoundItemRequest request) {
        if (request.getId() == null) {
            throw new EntityNotFoundException("Not found or item don't belong to user");
        }
    }


}
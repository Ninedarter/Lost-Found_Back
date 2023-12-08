package com.example.LostAndFoundApp.item.lost;

import com.example.LostAndFoundApp.item.coordinates.CoordinatesRepository;
import com.example.LostAndFoundApp.item.lost.request.LostItemRequest;
import com.example.LostAndFoundApp.item.lost.request.LostItemRequestAdd;
import com.example.LostAndFoundApp.item.lost.response.LostItemResponse;
import com.example.LostAndFoundApp.mapping.MappingService;
import com.example.LostAndFoundApp.user.User;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
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
            return new LostItemResponse(true, itemById, "FOUND BY ID " + id);
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

    public LostItemResponse addNew(LostItemRequestAdd request, Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        LostItem mappedItem = mappingService.mapLostItemNew(request, user);
        coordinatesRepository.save(mappedItem.getCoordinates());
        lostItemRepository.save(mappedItem);
        return new LostItemResponse(true, "Created successfully");
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


    //    User items CRUD
    public List<LostItem> getAllUserLostItems(String email) {
        List<LostItem> items = lostItemRepository.findByUser_Email(email);
        return items;
    }


    public LostItemResponse updateUserLostItem(LostItemRequest request) {

        List<LostItem> lostItems = lostItemRepository.findByUser_Email(request.getEmail());

        LostItem mappedItem = mappingService.mapLostItem(request);
        try {
            boolean doesBelongToUser = false;
            for (LostItem item : lostItems) {
                if (item.getId() == mappedItem.getId()) {
                    doesBelongToUser = true;
                    break;
                }
            }
            if (!doesBelongToUser) {
                return new LostItemResponse(false, "Item to update does not belongs to the user");
            } else {

                request.getCoordinates().setId(request.getId());
                request.setCoordinates(mappedItem.getCoordinates());
                coordinatesRepository.save(request.getCoordinates());
                lostItemRepository.save(mappedItem);
                return new LostItemResponse(true, "Updated successfully");
            }
        } catch (EntityNotFoundException e) {
            new LostItemResponse(false, "Item to update does not belongs to the user");

        }
        return new LostItemResponse(false, "Cannot update item");
    }


    public LostItemResponse deleteUserLostItem(LostItemRequest request) {
        try {
            checkDeleteRequest(request);
            if (isUserProperty(request)) {
                lostItemRepository.deleteById(request.getId());
                return new LostItemResponse(true, "Deleted successfully");
            } else {
                return new LostItemResponse(false, "Item not belonging to this user");
            }
        } catch (EntityNotFoundException e) {
            return new LostItemResponse(false, "Not found");
        }
    }


    boolean isUserProperty(LostItemRequest request) {
        Optional<LostItem> byIdAndUserEmail = lostItemRepository.findByIdAndUserEmail(request.getId(), request.getEmail());
        if (!doesExists(byIdAndUserEmail)) {
            throw new EntityNotFoundException("Not found");
        }
        return true;
    }

    void checkDeleteRequest(LostItemRequest request) {
        if (request.getId() == null) {
            throw new EntityNotFoundException("Not found or item don't belong to user");
        }
    }

//    private Long getIdByCoordinates(Double latitude, Double longitude) {
//        Optional<Long> idOptional = coordinatesRepository.findIdByLatitudeAndLongitude(latitude, longitude);
//        try {
//            if (!idOptional.isPresent()) {
//                throw new EntityNotFoundException();
//            }
//            return idOptional.get();
//        } catch (EntityNotFoundException e) {
//            return 0L;
//        }
//    }
}

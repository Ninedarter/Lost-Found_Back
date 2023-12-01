package com.example.LostAndFoundApp.mapping;

import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.coordinates.CoordinatesRepository;
import com.example.LostAndFoundApp.item.found.FoundItem;
import com.example.LostAndFoundApp.item.found.FoundItemRepository;
import com.example.LostAndFoundApp.item.found.request.FoundItemRequest;
import com.example.LostAndFoundApp.item.lost.LostItem;
import com.example.LostAndFoundApp.item.lost.LostItemRepository;
import com.example.LostAndFoundApp.item.lost.LostItemRequest;
import com.example.LostAndFoundApp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MappingService {

    private final UserRepository userRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final LostItemRepository lostItemRepository;
    private final FoundItemRepository foundItemRepository;

    public LostItem mapLostItem(LostItemRequest request) {
        LostItem item = new LostItem();
        item.setCategory(request.getCategory());
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setDateLost(request.getDateLost());
        item.setUser(userRepository.findByEmail(request.getEmail()).get());
        item.setDateLost(request.getDateLost());
        item.setCoordinates(new Coordinates(request.getLatitude(), request.getLongitude()));
        return item;
    }




    public FoundItem mapFoundItem(FoundItemRequest request) {
        FoundItem item = new FoundItem();
        item.setCategory(request.getCategory());
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setDateFound(request.getDateFound());
        item.setUser(userRepository.findByEmail(request.getEmail()).get());
        item.setDateFound(request.getDateFound());
        item.setCoordinates(new Coordinates(request.getLatitude(), request.getLongitude()));
        return item;
    }
}

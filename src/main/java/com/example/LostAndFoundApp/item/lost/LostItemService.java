package com.example.LostAndFoundApp.item.lost;

import com.example.LostAndFoundApp.item.lost.LostItem;
import com.example.LostAndFoundApp.item.lost.LostItemRepository;
import com.example.LostAndFoundApp.request.LostItemRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class LostItemService {

    private final LostItemRepository lostItemRepository;
    private final MappingService mappingService;

    public List<LostItem> getAll() {
        return lostItemRepository.findAll();
    }

    public LostItem add(LostItemRequest request) {
        LostItem item = mappingService.mapItem(request);
        lostItemRepository.save(item);
        return item;
    }

}

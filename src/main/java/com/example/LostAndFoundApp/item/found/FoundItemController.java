package com.example.LostAndFoundApp.item.found;

import com.example.LostAndFoundApp.item.found.request.FoundItemRequest;
import com.example.LostAndFoundApp.item.found.response.FoundItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/foundItem")

public class FoundItemController {

    private final FoundItemService foundItemService;


    @GetMapping("/getAll")
    public ResponseEntity<List<FoundItem>> getAll() {
        List<FoundItem> allFoundItems = foundItemService.getAll();
        return new ResponseEntity<>(allFoundItems, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<FoundItemResponse> add(@RequestBody FoundItemRequest foundItem) {
        System.out.println(foundItem);
        FoundItemResponse result = foundItemService.add(foundItem);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoundItemResponse> getById(@PathVariable("id") Long id) {
        FoundItemResponse result = foundItemService.getById(id);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<FoundItemResponse> update(@RequestBody FoundItemRequest foundItem) {
        foundItemService.update(foundItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<FoundItem> delete(@PathVariable("id") Long id) {
        foundItemService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
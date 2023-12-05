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

    @GetMapping("/all")
    public ResponseEntity<List<FoundItem>> getAll() {
        List<FoundItem> all = foundItemService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<FoundItemResponse> add(@RequestBody FoundItemRequest request) {
        FoundItemResponse response = foundItemService.add(request);
        return (response.isSuccess()) ? new ResponseEntity<>(response, HttpStatus.CREATED) : new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoundItemResponse> getById(@PathVariable("id") Long id) {
        FoundItemResponse response = foundItemService.getById(id);
        return (response.isSuccess()) ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<FoundItemResponse> update(@RequestBody FoundItemRequest request) {
        FoundItemResponse response = foundItemService.update(request);
        return (response.isSuccess()) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FoundItemResponse> delete(@PathVariable("id") Long id) {
        FoundItemResponse response = foundItemService.delete(id);
        return (response.isSuccess()) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/coordinates")
    public ResponseEntity<FoundItemResponse> getByCoordinates(@RequestBody FoundItemRequest request) {
        FoundItemResponse response = foundItemService.getByCoordinates(request);
        return (response.isSuccess()) ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    // Only user items CRUD

    @PostMapping("/user/item")
    public List<FoundItem> getByUserId(@RequestBody FoundItemRequest request) {
        return foundItemService.getAllUserFoundItems(request.getEmail());
    }

    @PutMapping("/user/updateItem")
    public ResponseEntity<FoundItemResponse> updateUserFoundItem(@RequestBody FoundItemRequest request) {
        FoundItemResponse response = foundItemService.updateUserFoundItem(request);
        return (response.isSuccess()) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/user/deleteItem")
    public ResponseEntity<FoundItemResponse> deleteUserFoundItem(@RequestBody FoundItemRequest request) {
        FoundItemResponse response = foundItemService.deleteUserFoundItem(request);
        return (response.isSuccess()) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
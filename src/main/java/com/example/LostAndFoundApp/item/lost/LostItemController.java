package com.example.LostAndFoundApp.item.lost;

import com.example.LostAndFoundApp.item.found.response.FoundItemResponse;
import com.example.LostAndFoundApp.item.lost.request.LostItemRequest;
import com.example.LostAndFoundApp.item.lost.response.LostItemResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/lostItem")
public class LostItemController {
    private final LostItemService lostItemService;


    @GetMapping("/all")
    public ResponseEntity<List<LostItem>> getAll() {
        List<LostItem> all = lostItemService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<LostItemResponse> add(@RequestBody LostItemRequest request) {
        LostItemResponse response = lostItemService.add(request);
        return (response.isSuccess()) ? new ResponseEntity<>(response, HttpStatus.CREATED) : new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LostItemResponse> getById(@PathVariable("id") Long id) {
        LostItemResponse response = lostItemService.getById(id);
        return (response.isSuccess()) ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<LostItemResponse> update(@RequestBody LostItemRequest request) {
        LostItemResponse response = lostItemService.update(request);
        return (response.isSuccess()) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<LostItemResponse> delete(@PathVariable("id") Long id) {
        LostItemResponse response = lostItemService.delete(id);
        return (response.isSuccess()) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    // Only user items CRUD

    @GetMapping("/user/item")
    public List<LostItem> getByUserId(@RequestBody LostItemRequest request) {
        return lostItemService.getAllUserFoundItems(request.getEmail());
    }


}

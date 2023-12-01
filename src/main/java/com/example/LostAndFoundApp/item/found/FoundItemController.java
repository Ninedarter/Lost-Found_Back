package com.example.LostAndFoundApp.item.found;
import com.example.LostAndFoundApp.item.found.FoundItem;
import com.example.LostAndFoundApp.item.found.FoundItemRepository;
import com.example.LostAndFoundApp.item.found.FoundItemRequest;
import com.example.LostAndFoundApp.item.found.FoundItemService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/foundItem")
public class FoundItemController {
    private FoundItemService service;
    @Autowired
    private FoundItemRepository foundItemRepository;

    @GetMapping("/getAll")
    public ResponseEntity<List<FoundItem>> getAll(){
        List<FoundItem> allFoundItems = service.getAll();
        return new ResponseEntity<>(allFoundItems, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<FoundItem> add(@RequestBody FoundItemRequest foundItem) {
        FoundItem addedFoundItem = service.add(foundItem);
        return new ResponseEntity<>(addedFoundItem, HttpStatus.CREATED);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<FoundItem> getById(@PathVariable("id") Long id) {
        FoundItem foundItem = service.getById(id);
        if (foundItem != null) {
            return new ResponseEntity<>(foundItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<FoundItem> update(@RequestBody FoundItemRequest foundItem) {
        service.update(foundItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<FoundItem> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
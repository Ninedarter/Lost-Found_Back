package com.example.LostAndFoundApp.item.lost;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/lostItem")
public class LostItemController {

    private final LostItemService service;


    @GetMapping("/getAll")
    public ResponseEntity<List<LostItem>> getAll() {
        List<LostItem> allLostItems = service.getAll();
        return new ResponseEntity<>(allLostItems, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<LostItem> add(@RequestBody LostItemRequest lostItem) {
        LostItem addedLostItem = service.add(lostItem);
        return new ResponseEntity<>(addedLostItem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LostItem> getById(@PathVariable("id") Long id) {
        LostItem lostItem = service.getById(id);
        if (lostItem != null) {
            return new ResponseEntity<>(lostItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<LostItem> update(@RequestBody LostItemRequest lostItem) {
        service.update(lostItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<LostItem> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

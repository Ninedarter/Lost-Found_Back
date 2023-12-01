package com.example.LostAndFoundApp.item.lost;
import com.example.LostAndFoundApp.item.lost.LostItem;
import com.example.LostAndFoundApp.item.lost.LostItemRepository;
import com.example.LostAndFoundApp.item.lost.LostItemService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/lostItem")
public class LostItemController {

    private LostItemService service;
    @Autowired
    private LostItemRepository lostItemRepository;

    @GetMapping("/getAll")
    public ResponseEntity<List<LostItem>> getAll(){
        List<LostItem> allLostItems = service.getAll();
        return new ResponseEntity<>(allLostItems, HttpStatus.OK);
    }

    @PostMapping ("/add")
    public ResponseEntity<LostItem> add(@RequestBody LostItemRequest lostItem) {
        LostItem addedLostItem = service.add(lostItem);
        return new ResponseEntity<>(addedLostItem, HttpStatus.CREATED);
    }

    @GetMapping ("/{id}")
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

package com.example.LostAndFoundApp.demo;

import com.example.LostAndFoundApp.item.found.FoundItem;
import com.example.LostAndFoundApp.item.found.FoundItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/demo-controller")
@RequiredArgsConstructor
public class DemoController {

    private final FoundItemService foundItemService;


    @GetMapping("/")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("All good");
    }

    @GetMapping("/all")
    public ResponseEntity<List<FoundItem>> getAll() {
        List<FoundItem> all = foundItemService.getAll();
        return ResponseEntity.ok(all);
    }


    @GetMapping("/byId")
    public ResponseEntity<FoundItem> getById(@RequestParam("itemId") Long id) {
        return ResponseEntity.ok(foundItemService.findById(id));
    }
}




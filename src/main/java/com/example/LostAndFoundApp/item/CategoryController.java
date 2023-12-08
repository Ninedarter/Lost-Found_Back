package com.example.LostAndFoundApp.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")

public class CategoryController {

    private final CategoryService CategoryService;

    @GetMapping("/all")
    public ResponseEntity<List<ItemCategory>> getCategoryList() {
        List<ItemCategory> all = CategoryService.getCategoryList();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

}

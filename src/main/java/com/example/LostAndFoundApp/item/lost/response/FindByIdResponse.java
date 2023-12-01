package com.example.LostAndFoundApp.item.lost.response;

import com.example.LostAndFoundApp.item.found.FoundItem;
import com.example.LostAndFoundApp.item.lost.LostItem;
import lombok.Getter;

@Getter
public class FindByIdResponse {

    private LostItem item;
    private String message;

    public FindByIdResponse(LostItem item, String message) {
        this.item = item;
        this.message = message;
    }
}

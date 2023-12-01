package com.example.LostAndFoundApp.item.found.response;

import com.example.LostAndFoundApp.item.found.FoundItem;
import lombok.Getter;

@Getter
public class FoundItemResponse {

    private boolean success;
    private FoundItem item;
    private String message;


    public FoundItemResponse(FoundItem item, String message, boolean success) {
        this.item = item;
        this.message = message;
        this.success = success;
    }

    public FoundItemResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public FoundItemResponse(String message) {
        this.message = message;
    }



}

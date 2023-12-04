package com.example.LostAndFoundApp.item.found.response;

import com.example.LostAndFoundApp.item.found.FoundItem;
import lombok.Getter;

import java.util.List;

@Getter
public class FoundItemResponse {

    private boolean success;
    private FoundItem item;
    private String message;
    private List<FoundItem> allFound;

    public FoundItemResponse(boolean success, FoundItem item, String message) {
        this.item = item;
        this.message = message;
        this.success = success;
    }

    public FoundItemResponse(boolean success, FoundItem items) {
        this.success = success;
        this.item = items;
    }

    public FoundItemResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public FoundItemResponse(boolean success, String message, List<FoundItem> allFound) {
        this.success = success;
        this.message = message;
        this.allFound = allFound;
    }

    public FoundItemResponse(String message) {
        this.message = message;
    }

    public FoundItemResponse(boolean success) {
        this.success = success;
    }

    public FoundItemResponse(List<FoundItem> allFound) {
        this.allFound = allFound;
    }

}

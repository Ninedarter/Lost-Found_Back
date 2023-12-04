package com.example.LostAndFoundApp.item.lost.response;

import com.example.LostAndFoundApp.item.lost.LostItem;
import lombok.Getter;

import java.util.List;

@Getter
public class LostItemResponse {

    private boolean success;
    private LostItem item;
    private String message;
    private List<LostItem> allFound;

    public LostItemResponse(boolean success, LostItem item, String message) {
        this.success = success;
        this.item = item;
        this.message = message;
    }

    public LostItemResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public LostItemResponse(boolean success, String message, List<LostItem> allFound) {
        this.success = success;
        this.message = message;
        this.allFound = allFound;
    }

    public LostItemResponse(boolean success) {
        this.success = success;
    }

    public LostItemResponse(List<LostItem> allFound) {
        this.allFound = allFound;
    }


}
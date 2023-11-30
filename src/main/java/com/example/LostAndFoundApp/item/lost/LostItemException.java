package com.example.LostAndFoundApp.item.lost;

public class LostItemException extends  RuntimeException{

    public LostItemException() {
    }

    public LostItemException(String message) {
        super(message);
    }
}

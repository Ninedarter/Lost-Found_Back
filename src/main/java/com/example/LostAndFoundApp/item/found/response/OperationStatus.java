package com.example.LostAndFoundApp.item.found.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationStatus {

    private boolean status;
    private String message;

    public OperationStatus(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}

package com.example.LostAndFoundApp.item.lost.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationStatus {

    private boolean isSuccess;
    private String message;

    public OperationStatus(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }
}

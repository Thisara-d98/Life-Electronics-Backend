package com.musicly.store.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MessageResponse {

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}

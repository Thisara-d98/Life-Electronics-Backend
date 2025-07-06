package com.musicly.store.Models;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}

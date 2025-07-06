package com.musicly.store.Models;

import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String password;
    private String Role;
}

package com.musicly.store.Models;

public class LoginRequest {
    private String email;
    private String password;

    public LoginRequest(){

    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}

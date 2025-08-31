package com.musicly.store.Models;

public class SignupRequest {
    private String userName;
    private String email;
    private String password;
    private String role;

    public SignupRequest() {
        String userName = "";
        String email = "";
        String password = "";
        String role = "";
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName(){
        return userName;
    }

    public String getRole() {
        return role;
    }
}

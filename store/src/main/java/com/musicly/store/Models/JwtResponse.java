package com.musicly.store.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private int id;
    private String email;
    private String role;

    public JwtResponse(String accessToken, String email, String id, String role) {
        this.token = accessToken;
        this.email = email;
        this.id = Integer.parseInt(id);
        this.role = role;
    }
}

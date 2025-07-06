package com.musicly.store.Domain.User;

import javax.management.relation.Role;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;


public class User {
    @Id

    @Column(unique = true, nullable = false)
    private String UserName;
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private String Password;
    @Column(unique = true, nullable=false)
    private String Email;
    @Enumerated(EnumType.STRING)
    @Column(nullable= false)
    private Role role;

    public String getUserName() {
        return UserName;
    }
    public void setUserName(String userName) {
        UserName = userName;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User() {

    }
}

package com.musicly.store.Services;

import java.util.List;

import com.musicly.store.Domain.User.User;

public interface UserService {
    public List<User> GetAllUsers();
    public User AddUser(User user);
    public User UpdateUser(User user);
    public void DeleteUser(int id);
    public User GetUserByUserName(String userName);
    public User GetUserById(int id);
}

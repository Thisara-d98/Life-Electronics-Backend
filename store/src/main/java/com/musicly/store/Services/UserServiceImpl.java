package com.musicly.store.Services;

import java.util.List;

import com.musicly.store.Domain.User.User;
import com.musicly.store.Domain.User.UserRepository;
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> GetAllUsers() {
        List<User> user = userRepository.findAll();
        return user;
    }

    @Override
    public User GetUserById(int id) {
        User user = userRepository.findUserById(0);
        return user;
    }

    @Override
    public User AddUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User UpdateUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void DeleteUser(int id) {
        User user = userRepository.findUserById(id);
        userRepository.delete(user);
    }

    @Override
    public User GetUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }
}
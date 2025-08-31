package com.musicly.store.Services;

import java.util.List;
import java.util.Optional;

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
    public Optional<User> GetUserById(int id) {
        Optional<User> user = userRepository.findUserById(0);
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
        Optional<User> user = userRepository.findUserById(id);
        if(user.isPresent()){
            userRepository.delete(user.get());
        }
        
    }

    @Override
    public Optional<User> GetUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }
}
package com.musicly.store.Domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findUserByUserName(String userName);
    User findUserByEmail(String email);
    User findUserById(int id);
}

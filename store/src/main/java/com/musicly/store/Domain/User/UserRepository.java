package com.musicly.store.Domain.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUserName(String userName);
    Optional<User> findByEmail(String email);
    Optional<User> findUserById(int id);
    boolean existsByEmail(String email);
}

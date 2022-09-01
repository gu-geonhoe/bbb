package com.example.apitest.User.repository;

import com.example.apitest.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    User findByUserName(String userName);

    Optional<User> findByPassword(String password);
}

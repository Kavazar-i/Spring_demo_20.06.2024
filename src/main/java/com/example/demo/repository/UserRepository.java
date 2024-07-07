package com.example.demo.repository;

import com.example.demo.model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String userName);

    Optional<User> findById(Long id);

    User save(@NotNull User user);

    void deleteById(Long id);
}
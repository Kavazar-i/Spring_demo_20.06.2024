package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.NameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final NameRepository nameRepository;

    public UserService(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    public User saveUser(User user) {
        return nameRepository.save(user);
    }

    public List<User> findAll() {
        return nameRepository.findAll();
    }
}

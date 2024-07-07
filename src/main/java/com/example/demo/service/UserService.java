package com.example.demo.service;

import com.example.demo.controller.LoginDto;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
                       RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public Optional<String> signin(String username, String password) {
        log.info("User attempting to sign in");
        Optional<String> token = Optional.empty();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            if (!password.equals(user.get().getPassword())) {
                log.info("Log in failed for user {}", username);
                return token;
            }
            try {
                token = Optional.of(jwtProvider.createToken(username, user.get().getRoles()));
            } catch (AuthenticationException e) {
                log.info("Log in failed for user {}", username);
            }
        }
        return token;
    }

    public Optional<User> signup(String username, String password, String firstName, String lastName) {
        log.info("New user attempting to sign up");
        Optional<User> user = Optional.empty();
        if (userRepository.findByUsername(username).isEmpty()) {
            Optional<Role> role = roleRepository.findByRoleName("ROLE_USER");
            if (role.isEmpty()) {
                role = Optional.of(roleRepository.save(new Role("ROLE_USER", "User role")));
            }
            List<Role> listOfRoles = role.map(List::of).orElseGet(List::of);
            user = Optional.of(userRepository.save(
                    new User(
                            username,
                            password,
                            firstName,
                            lastName,
                            listOfRoles)));
        }
        return user;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, LoginDto loginDto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(loginDto.getUsername());
            user.setPassword(passwordEncoder.encode(loginDto.getPassword()));
            user.setFirstName(loginDto.getFirstName());
            user.setLastName(loginDto.getLastName());
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }
}

package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<String> login(@Valid LoginDto loginDto) throws JsonProcessingException, UnsupportedEncodingException {
        Optional<String> token = userService.signin(loginDto.getUsername(), loginDto.getPassword());

        List<User> userList = userService.getAll();

        ObjectMapper mapper = new ObjectMapper();
        String userListJson = mapper.writeValueAsString(userList);

        String encodedJson = URLEncoder.encode(userListJson, StandardCharsets.UTF_8);


        HttpHeaders headers = new HttpHeaders();
        if (token.isPresent()) {
            headers.add("Authorization", "Bearer " + token.get());

        }

        URI location = UriComponentsBuilder.fromUriString("/user-list.html")
                .queryParam("users", encodedJson)
                .build()
                .toUri();

        ResponseEntity<String> responseEntity = ResponseEntity.status(HttpStatus.FOUND)
                .location(location)
                .headers(headers)
                .build();
        return responseEntity;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> signup(@Valid LoginDto loginDto) throws JsonProcessingException {
        userService.signup(loginDto.getUsername(), loginDto.getPassword(), loginDto.getFirstName(), loginDto.getLastName())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User already exists"));

        URI location = UriComponentsBuilder.fromUriString("/signin.html")
                .build()
                .toUri();

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(location)
                .build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public User updateUser(@PathVariable Long id, @Valid LoginDto loginDto) {
        return userService.updateUser(id, loginDto);
    }
}

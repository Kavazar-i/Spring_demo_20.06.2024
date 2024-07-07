package com.example.demo.controller;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
public class LoginDto {
    @Setter
    @NotNull
    private String username;

    @Setter
    @NotNull
    private String password;

    @Setter
    private String firstName;

    @Setter
    private String lastName;


    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public LoginDto(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

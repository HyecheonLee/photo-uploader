package com.hyecheon.photouploader.security;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "email cannot be blank")
    private String email;
    @NotBlank(message = "password cannot be blank")
    private String password;

    public LoginRequest(@NotBlank(message = "Username cannot be blank") String email) {
        this.email = email;
    }
}
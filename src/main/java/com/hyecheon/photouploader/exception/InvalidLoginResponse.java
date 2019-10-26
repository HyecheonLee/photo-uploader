package com.hyecheon.photouploader.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class InvalidLoginResponse {
    private String username = "Invalid Username";
    private String password = "Invalid Password";
}
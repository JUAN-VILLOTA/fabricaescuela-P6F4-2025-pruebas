package com.fabricaescuela.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String correo;
    private String role; // optional
}

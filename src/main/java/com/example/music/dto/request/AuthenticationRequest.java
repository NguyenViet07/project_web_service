package com.example.music.dto.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String userName;
    private String password;
}

package com.example.music.dto.request;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;

    private Integer page = 1;
    private Integer size = 10;
}

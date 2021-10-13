package com.example.music.dto.response;

import com.example.music.dto.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDto {

    private UserInfo userInfo;
    private String roleName;
    private String token;
}

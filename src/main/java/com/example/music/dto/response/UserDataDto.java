package com.example.music.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDto {
    private Long userId;
    private String userName;
    private String fullName;
    private String tokenId;

}

package com.example.music.dto.response;

import com.example.music.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserViewDto {

    private Long userId;

    private String name;

    private String image;

    private String username;

    private String address;

    private String identityCard;

    private String identityCardImage;

    private String company;

    private Integer isSinger;

    private Integer isActive;

    private String createDate;

    private String updateDate;

    private String roleName;
}

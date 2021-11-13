package com.example.music.dto.request;

import com.example.music.model.Role;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
public class UserRequest {
    private String username;
    private String password;

    private Long userId;

    private String name;

    private String image;

    private String address;

    private String identityCard;

    private String identityCardImage;

    private String company;

    private Long roleId;

    private Long isSinger;

    private Integer isActive;

    private Integer page = 1;
    private Integer size = 10;
}

package com.example.music.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "identity_card")
    private String identityCard;

    @Column(name = "identity_card_image")
    private String identityCardImage;

    @Column(name = "company")
    private String company;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role roles;

}

package com.example.music.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "playlist")
@Data
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_playlist")
    private Long playlistId;

    @Column(name = "name_playlist")
    private String namePlaylist;

    @Column(name = "image")
    private String image;

    @Column(name = "user_id")
    private Long userId;

}

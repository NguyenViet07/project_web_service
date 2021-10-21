package com.example.music.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "created", updatable = false)
    @CreationTimestamp
    private Date createDate;

    @Column(name = "updated")
    @UpdateTimestamp
    private Date updateDate;
}

package com.example.music.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "song")
@Data
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Long songId;

    @Column(name = "song_name", nullable = false)
    private String songName;

    @Column(name = "link")
    private String link;

    @Column(name = "image")
    private String image;

    @Column(name = "views")
    private Long views;

    @Column(name = "style")
    private Long style;

    @Column(name = "type")
    private Long type;

    @Column(name = "description")
    private String description;

    @Column(name = "create", updatable = false)
    @CreationTimestamp
    private Date createDate;

    @Column(name = "updated")
    @UpdateTimestamp
    private Date updateDate;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "album_id")
    private Long albumId;

}

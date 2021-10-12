package com.example.music.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "album")
@Data
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long albumId;

    @Column(name = "name_album")
    private String albumName;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "create", updatable = false)
    @CreationTimestamp
    private Date createDate;

    @Column(name = "updated")
    @UpdateTimestamp
    private Date updateDate;
}

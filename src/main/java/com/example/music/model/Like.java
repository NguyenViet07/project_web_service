package com.example.music.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "like")
@Data
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long likeId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "song_id")
    private Long songId;

}

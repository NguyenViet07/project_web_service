package com.example.music.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "like")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "description")
    private String description;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "song_id")
    private Long songId;

}

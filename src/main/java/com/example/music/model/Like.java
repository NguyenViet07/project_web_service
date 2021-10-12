package com.example.music.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "create", updatable = false)
    @CreationTimestamp
    private Date createDate;

    @Column(name = "updated")
    @UpdateTimestamp
    private Date updateDate;
}

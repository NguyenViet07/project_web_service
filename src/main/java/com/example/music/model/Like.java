package com.example.music.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "like_song")
@Data
public class Like {

    @EmbeddedId
    private LikeUserSongId id;

    @Column(name = "created", updatable = false)
    @CreationTimestamp
    private Date createDate;

    @Column(name = "updated")
    @UpdateTimestamp
    private Date updateDate;

    @Data
    @Embeddable
    public static class LikeUserSongId implements Serializable {
        @Column(name = "user_id")
        private Long userId;

        @Column(name = "song_id")
        private Long songId;
    }
}





package com.example.music.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "song_playlist")
@Data
public class SongPlaylist {
    @EmbeddedId
    private PlaylistSongId playlistSongId;

    @Column(name = "created", updatable = false)
    @CreationTimestamp
    private Date createDate;

    @Column(name = "updated")
    @UpdateTimestamp
    private Date updateDate;

    @Data
    @Embeddable
    public static class PlaylistSongId implements Serializable {
        @Column(name = "playlist_id")
        private Long playlistId;

        @Column(name = "song_id")
        private Long songId;
    }
}

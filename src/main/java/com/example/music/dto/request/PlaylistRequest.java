package com.example.music.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistRequest {

    private Long playlistId;

    private List<Long> listSongId;
    private Long userId;
    private String namePlaylist;

    private Integer page = 1;
    private Integer size = 10;
}

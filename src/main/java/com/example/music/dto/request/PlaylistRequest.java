package com.example.music.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistRequest {

    private Long playlistId;

    private List<Long> listSongId;
    private Long userId;
    private String namePlayList;

    private Integer page = 1;
    private Integer size = 10;
}

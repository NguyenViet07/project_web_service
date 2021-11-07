package com.example.music.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AlbumRequest {

    private Long albumId;

    private List<Long> listSongId;
    private Long userId;
    private String nameAlbum;

    private Integer page = 1;
    private Integer size = 10;
}

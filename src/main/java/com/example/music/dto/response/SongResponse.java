package com.example.music.dto.response;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SongResponse {
    private Long songId;
    private String songName;
    private String image;
    private Long style;
    private Long type;
    private String description;
    private String url;
    private String link;
    private Long view;
    private Long albumId;
    private Integer like;
    private String albumName;
    private String singerName;
    private String createDate;
}

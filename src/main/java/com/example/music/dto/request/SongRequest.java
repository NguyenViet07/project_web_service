package com.example.music.dto.request;

import com.example.music.model.Song;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SongRequest {
    private Long songId;
    private MultipartFile dataSongValue;
    private String songName;
    private String image;
    private Long style;
    private Long type;
    private String description;
    private String url;
    private Long albumId;
    private Integer like;

    private Integer page = 1;
    private Integer size = 10;
}

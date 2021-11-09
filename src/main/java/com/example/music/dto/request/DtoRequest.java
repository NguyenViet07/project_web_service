package com.example.music.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DtoRequest {
    private Long songId;

    private Long albumId;

    private Long playListId;

    private Integer page = 1;
    private Integer size = 10;
}

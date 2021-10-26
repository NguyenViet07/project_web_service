package com.example.music.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CommentRequest {

    private Long commentId;

    private Long songId;
    private Long userId;
    private String description;

    private Integer page = 1;
    private Integer size = 10;
}

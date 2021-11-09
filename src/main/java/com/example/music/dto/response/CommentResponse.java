package com.example.music.dto.response;

import lombok.Data;

@Data
public class CommentResponse {
    private Long commentId;
    private String description;
    private String image;
    private String userName;
    private String createDate;
}

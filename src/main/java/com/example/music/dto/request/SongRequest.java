package com.example.music.dto.request;

import com.example.music.model.Song;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SongRequest {
    private Song song;
    private MultipartFile dataSongValue;
}

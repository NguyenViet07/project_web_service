package com.example.music.dto.response;

import com.example.music.model.Album;
import com.example.music.model.Playlist;
import com.example.music.model.Song;
import lombok.Data;

import java.util.List;

@Data
public class DtoResponse {
    private Album album;
    private Playlist playlist;
    private List<Song> songList;
    private List<SongResponse> songResponseList;
    private String userName;

}

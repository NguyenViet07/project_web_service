package com.example.music.service;

import com.example.music.config.JwtTokenProvider;
import com.example.music.model.Album;
import com.example.music.model.Playlist;
import com.example.music.model.User;
import com.example.music.repositories.PlaylistRepository;
import com.example.music.repositories.SongRepository;
import com.example.music.response.ResponseCode;
import com.example.music.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;


    public ResponseResult saveOrUpdate(Playlist playlist, String token) {
        try {
            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);

            playlist.setUserId(user.getUserId());
            playlistRepository.save(playlist);
            return ResponseResult.success(null);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

}

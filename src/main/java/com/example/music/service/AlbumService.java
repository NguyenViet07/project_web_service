package com.example.music.service;

import com.example.music.config.JwtTokenProvider;
import com.example.music.dto.request.SongRequest;
import com.example.music.model.Album;
import com.example.music.model.Song;
import com.example.music.model.User;
import com.example.music.repositories.AlbumRepository;
import com.example.music.repositories.SongRepository;
import com.example.music.response.ResponseCode;
import com.example.music.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;


    @Autowired
    private JwtTokenProvider tokenProvider;


    public ResponseResult saveOrUpdate(Album album, String token) {
        try {
            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);

            album.setUserId(user.getUserId());
            albumRepository.save(album);
            return ResponseResult.success(null);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

}

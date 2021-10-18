package com.example.music.service;

import com.example.music.dto.request.SongRequest;
import com.example.music.model.Song;
import com.example.music.repositories.SongRepository;
import com.example.music.response.ResponseCode;
import com.example.music.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;

@Service
@Slf4j
public class SongService {

    @Autowired
    private SongRepository songRepository;


    @Transient
    public ResponseResult saveOrUpdate(SongRequest songRequest, String token) {
        try {
            // lưu data bài hát

            // lưu thông tin bài hát
            Song song = songRequest.getSong();
//            song.setUserId();

            songRepository.save(songRequest.getSong());
            return ResponseResult.success(null);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }

    }

}

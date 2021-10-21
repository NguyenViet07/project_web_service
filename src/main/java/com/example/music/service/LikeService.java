package com.example.music.service;

import com.example.music.config.JwtTokenProvider;
import com.example.music.dto.request.SongRequest;
import com.example.music.model.Like;
import com.example.music.model.Song;
import com.example.music.model.User;
import com.example.music.repositories.LikeRepository;
import com.example.music.repositories.SongRepository;
import com.example.music.response.ResponseCode;
import com.example.music.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public ResponseResult save(Long songId, String token) {
        try {
            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);

            if (user == null) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bạn phải đăng nhập");
                return new ResponseResult(responseCode);
            }


            Like like = new Like();

            Like.LikeUserSongId likeUserSongId = new Like.LikeUserSongId();
            likeUserSongId.setSongId(songId);
            likeUserSongId.setUserId(user.getUserId());
            like.setId(likeUserSongId);

            likeRepository.save(like);

            return ResponseResult.success(null);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    @Transactional
    public ResponseResult deleteLike(Long songId, String token) {
        try {
            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);

            if (user == null) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bạn phải đăng nhập");
                return new ResponseResult(responseCode);
            }
            likeRepository.deleteLikeAllById(user.getUserId(), songId);
            return ResponseResult.success(null);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }



}

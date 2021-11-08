package com.example.music.service;

import com.example.music.config.JwtTokenProvider;
import com.example.music.dto.request.AlbumRequest;
import com.example.music.model.Album;
import com.example.music.model.User;
import com.example.music.repositories.AlbumRepository;
import com.example.music.repositories.SongRepository;
import com.example.music.response.ResponseCode;
import com.example.music.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongService songService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public ResponseResult save(Album album, String token) {
        try {
            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);

            if (user == null) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bạn phải đăng nhập");
                return new ResponseResult(responseCode);
            }
            album.setUserId(user.getUserId());

            albumRepository.save(album);

            return ResponseResult.success(null);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    public ResponseResult addSongToAlbum(AlbumRequest albumRequest, String token) {
        try {
            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);

            if (user == null) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bạn phải đăng nhập");
                return new ResponseResult(responseCode);
            }
            List<Long> listSongId = albumRequest.getListSongId();
            Album album = albumRepository.findAllByAlbumId(albumRequest.getAlbumId());
            if (user.getRoleId() != 1l && user.getUserId() != album.getUserId()) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bạn chỉ được thêm album của mình");
                return new ResponseResult(responseCode);
            }
            if (listSongId.size() > 0) {
                for (Long songId : listSongId ) {
                    songService.addSongAlbum(songId, album.getAlbumId());
                }
            }
            return ResponseResult.success(null);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }


}

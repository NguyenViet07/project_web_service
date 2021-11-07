package com.example.music.service;

import com.example.music.config.JwtTokenProvider;
import com.example.music.dto.request.AlbumRequest;
import com.example.music.dto.request.PlaylistRequest;
import com.example.music.model.Album;
import com.example.music.model.Playlist;
import com.example.music.model.User;
import com.example.music.repositories.PlaylistRepository;
import com.example.music.repositories.SongPlaylistRepository;
import com.example.music.repositories.SongRepository;
import com.example.music.response.ResponseCode;
import com.example.music.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongService songService;


    @Autowired
    private JwtTokenProvider tokenProvider;

    public ResponseResult save(Playlist playlist, String token) {
        try {
            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);

            if (user == null) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bạn phải đăng nhập");
                return new ResponseResult(responseCode);
            }
            playlist.setUserId(user.getUserId());

            playlistRepository.save(playlist);

            return ResponseResult.success(null);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    public ResponseResult addSongToPlaylist(PlaylistRequest playlistRequest, String token) {
        try {
            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);

            if (user == null) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bạn phải đăng nhập");
                return new ResponseResult(responseCode);
            }
            List<Long> listSongId = playlistRequest.getListSongId();
            Playlist playlist = playlistRepository.findAllByPlaylistId(playlistRequest.getPlaylistId());
            if (user.getRoleId() != 1l && user.getUserId() != playlist.getUserId()) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bạn chỉ được thêm album của mình");
                return new ResponseResult(responseCode);
            }
            if (listSongId.size() > 0) {
                for (Long songId : listSongId ) {
                    songService.addSongPlayList(songId, playlist.getPlaylistId());
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

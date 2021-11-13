package com.example.music.service;

import com.example.music.config.JwtTokenProvider;
import com.example.music.dto.request.AlbumRequest;
import com.example.music.dto.response.DtoResponse;
import com.example.music.dto.response.SongResponse;
import com.example.music.model.Album;
import com.example.music.model.Song;
import com.example.music.model.User;
import com.example.music.repositories.AlbumRepository;
import com.example.music.repositories.SongRepository;
import com.example.music.repositories.UserRepository;
import com.example.music.response.ResponseCode;
import com.example.music.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;

@Service
@Slf4j
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepository userRepository;


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

    public ResponseResult getListAlbum() {
        try {
            List<Album> list = albumRepository.getListAlbum();
            return ResponseResult.success(list);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    public ResponseResult getListMyAlbum(AlbumRequest albumRequest, String token) {
        try {
            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);

            if (user == null) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bạn phải đăng nhập");
                return new ResponseResult(responseCode);
            }


            List<Album> list = albumRepository.findAllByUserId(user.getUserId());

            return ResponseResult.success(list);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    @Transient
    public ResponseResult deleteAlbum(AlbumRequest albumRequest, String token) {
        try {

            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);

            if (user == null) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bạn phải đăng nhập");
                return new ResponseResult(responseCode);
            }


            Album album = albumRepository.findAllByAlbumId(albumRequest.getAlbumId());

            List<Long> list = songRepository.getListSongId(album.getAlbumId());

            if (list.size() > 0) {
                songRepository.deleteAlbum(list);
            }

            if (album == null) {
                return new ResponseResult(ResponseCode.ERROR);
            } else {
                albumRepository.delete(album);
                return ResponseResult.success(null);
            }
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    public ResponseResult getAlbum(AlbumRequest albumRequest) {
        try {

            Album album = albumRepository.findAllByAlbumId(albumRequest.getAlbumId());

            List<Song> list = songRepository.getListSongByAlbumId(album.getAlbumId());

            User userAlbum = userRepository.findAllByUserId(album.getUserId());

            DtoResponse dtoResponse = new DtoResponse();

            dtoResponse.setAlbum(album);
            dtoResponse.setSongList(list);
            dtoResponse.setUserName(userAlbum.getUsername());

            return ResponseResult.success(dtoResponse);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    public ResponseResult getInfoAlbum(AlbumRequest albumRequest, String token) {
        try {
            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);

            if (user == null) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bạn phải đăng nhập");
                return new ResponseResult(responseCode);
            }

            Album album = albumRepository.findAllByAlbumId(albumRequest.getAlbumId());

            List<Song> list = songRepository.getListSongByAlbumId(album.getAlbumId());

            User userAlbum = userRepository.findAllByUserId(album.getUserId());

            DtoResponse dtoResponse = new DtoResponse();

            dtoResponse.setAlbum(album);
            dtoResponse.setSongList(list);
            dtoResponse.setUserName(userAlbum.getUsername());

            return ResponseResult.success(dtoResponse);
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

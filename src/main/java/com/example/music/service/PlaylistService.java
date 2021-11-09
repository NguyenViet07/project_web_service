package com.example.music.service;

import com.example.music.config.JwtTokenProvider;
import com.example.music.dto.request.AlbumRequest;
import com.example.music.dto.request.PlaylistRequest;
import com.example.music.dto.request.SongRequest;
import com.example.music.dto.response.CommentResponse;
import com.example.music.dto.response.DtoResponse;
import com.example.music.dto.response.SongResponse;
import com.example.music.model.Album;
import com.example.music.model.Playlist;
import com.example.music.model.Song;
import com.example.music.model.User;
import com.example.music.repositories.PlaylistRepository;
import com.example.music.repositories.SongPlaylistRepository;
import com.example.music.repositories.SongRepository;
import com.example.music.repositories.UserRepository;
import com.example.music.response.ResponseCode;
import com.example.music.response.ResponseResult;
import com.example.music.untils.RepositoryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongService songService;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public ResponseResult saveOrUpdate(Playlist playlist, String token) {
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


    public ResponseResult getListMyPlaylist(PlaylistRequest playlistRequest, String token) {
        try {
            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);

            if (user == null) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bạn phải đăng nhập");
                return new ResponseResult(responseCode);
            }

            List<Playlist> list = playlistRepository.findAllByUserId(user.getUserId());

            return ResponseResult.success(list);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    public ResponseResult getInfoPlaylist(PlaylistRequest playlistRequest, String token) {
        try {
            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);

            if (user == null) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bạn phải đăng nhập");
                return new ResponseResult(responseCode);
            }

            Playlist album = playlistRepository.findAllByPlaylistId(playlistRequest.getPlaylistId());

            List<Object[]> list = songRepository.getListSongByPlaylistId(album.getPlaylistId());

            User userPlaylist = userRepository.findAllByUserId(album.getUserId());

            DtoResponse dtoResponse = new DtoResponse();

            dtoResponse.setPlaylist(album);
            dtoResponse.setSongResponseList(getListSongPlaylist(list));
            dtoResponse.setUserName(userPlaylist.getUsername());

            return ResponseResult.success(dtoResponse);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    public List<SongResponse> getListSongPlaylist(List<Object[]> list) {
        if (list != null) {
            List<SongResponse> rs = new ArrayList<>();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            SongResponse tinyItem;
            int index;
            for (Object[] row : list) {
                index = 0;
                tinyItem = new SongResponse();
                tinyItem.setSongId(RepositoryUtil.getLongFromBigInteger(row[index++]));
                tinyItem.setSongName(RepositoryUtil.getStringByValue(row[index++]));
                tinyItem.setLink(RepositoryUtil.getStringByValue(row[index++]));
                tinyItem.setImage(RepositoryUtil.getStringByValue(row[index++]));
                tinyItem.setView(RepositoryUtil.getLongFromBigInteger(row[index++]));
                tinyItem.setDescription(RepositoryUtil.getStringByValue(row[index++]));
                tinyItem.setCreateDate(RepositoryUtil.getTimestampToString(row[index++], dateFormat));
                tinyItem.setSingerName(RepositoryUtil.getStringByValue(row[index++]));
                rs.add(tinyItem);
            }
            return rs;
        }
        return null;
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
                responseCode.setMessage("Bạn chỉ được thêm playlist của mình");
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

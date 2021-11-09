package com.example.music.service;

import com.example.music.config.JwtTokenProvider;
import com.example.music.dto.request.DtoRequest;
import com.example.music.dto.request.SongRequest;
import com.example.music.dto.response.SongResponse;
import com.example.music.model.*;
import com.example.music.repositories.*;
import com.example.music.response.ResponseCode;
import com.example.music.response.ResponseResult;
import com.example.music.untils.RepositoryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;


    @Autowired
    private SongPlaylistRepository songPlaylistRepository;

    @Value("${upload.dir}")
    private String uploadPath;


    @Transient
    public ResponseResult saveOrUpdate(SongRequest songRequest, String token) {
        try {
            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);
            // lưu data bài hát
            String path = uploadFile(songRequest.getDataSongValue(), user.getUsername(), songRequest.getSongName());
            // lưu thông tin bài hát
            Song song = new Song();
            song.setImage(songRequest.getImage());
            song.setSongName(songRequest.getSongName());
            song.setDescription(songRequest.getDescription());
            song.setUserId(user.getUserId());
            song.setLink(path);
            songRepository.save(song);
            return ResponseResult.success(null);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    public ResponseResult upView(Long songId) {
        try {
            // lưu thông tin bài hát
            Song song = songRepository.findAllBySongId(songId);
            Long view = song.getViews();
            if (view == null) {
                view = 1l;
            } else {
                view = ++view;
            }
            song.setViews(view);
            songRepository.save(song);
            return ResponseResult.success(null);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    public String uploadFile(MultipartFile file, String userName, String songName) {
        try {
            // tạo file name
            String fileName = songName + ".mp3";
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String getDateToString = dateFormat.format(date);
            // tạo path folder
            String pathFolder = uploadPath + '/' + userName + '/' + getDateToString;
            // tạo folder
            File folder = new File(pathFolder);
            if(!folder.exists()) {
                folder.mkdirs(); // check và tạo nhiều thư mực
            }
            // tạo file lưu
            File fileSave = new File(pathFolder, fileName);
            System.out.println("PATH:  " + fileSave.getAbsolutePath());
//            ghi file
            FileOutputStream fileOutputStream = new FileOutputStream(fileSave);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
            return pathFolder + "/"+ fileName;
        } catch (IOException e) {
            log.error("Có lỗi: " + e.getMessage());
        }
        return null;
    }

    public ResponseResult getListSongByUserId(SongRequest songRequest, String token) {
        try {
            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);

            PageRequest page = PageRequest.of(songRequest.getPage() - 1, songRequest.getSize());

            Page<Song> songPage = songRepository.findAllByUserId(user.getUserId(), page);

            return ResponseResult.success(songPage);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }


    public ResponseResult getListSongCreated() {
        try {
            // lấy thông tin user
            List<Song> list = songRepository.getListSongCreated();
            return ResponseResult.success(list);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    public ResponseResult getListSongView() {
        try {
            // lấy thông tin user
            List<Song> list = songRepository.getListSongView();
            return ResponseResult.success(list);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    public ResponseResult findBySongId(Long songId, String token) {
        try {

            Song song = songRepository.findAllBySongId(songId);

            SongResponse songResponse = new SongResponse();
            songResponse.setSongName(song.getSongName());
            songResponse.setDescription(song.getDescription());
            songResponse.setUrl(song.getLink());
            songResponse.setImage(song.getImage());

            if (song.getAlbumId() != null) {
                Album album = albumRepository.findAllByAlbumId(song.getAlbumId());
                songResponse.setAlbumName(album.getAlbumName());
            }

            User singer = userRepository.findAllByUserId(song.getUserId());
            songResponse.setSingerName(singer.getName());


            songResponse.setLike(0);

            if (token != null) {
                User user = tokenProvider.getUserIdFromJWT(token);
                if (user != null) {
                    Like like = likeRepository.findLikeById(user.getUserId(), songId);
                    if (like != null) {
                        songResponse.setLike(1);
                    }
                }
            }
            return ResponseResult.success(songResponse);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }


    public Song addSongAlbum(Long songId, Long alBumId) {

        Song song = songRepository.findAllBySongId(songId);

        song.setAlbumId(alBumId);

        song = songRepository.save(song);

        return song;

    }

    public ResponseResult addSongToAlbum(DtoRequest dtoRequest, String token) {
        try {
            Song song = songRepository.findAllBySongId(dtoRequest.getSongId());
            if (song.getAlbumId() != null) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bài hát đã nằm trong một album khác");
                return new ResponseResult(responseCode);
            }
            song.setAlbumId(dtoRequest.getAlbumId());

            song = songRepository.save(song);

            return ResponseResult.success(song);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    public SongPlaylist addSongPlayList(Long songId, Long playListId) {

        SongPlaylist.PlaylistSongId id = new SongPlaylist.PlaylistSongId();

        id.setSongId(songId);
        id.setPlaylistId(playListId);

        SongPlaylist songPlaylist = new SongPlaylist();
        songPlaylist.setPlaylistSongId(id);

        songPlaylist = songPlaylistRepository.save(songPlaylist);

        return songPlaylist;

    }

    public ResponseResult addSongToPlayList(DtoRequest dtoRequest, String token) {
        try {

            SongPlaylist songPlaylistCheck = songPlaylistRepository.findAllBySongIdAndPlId(dtoRequest.getSongId(), dtoRequest.getPlayListId());

            if (songPlaylistCheck != null) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bài hát đã được thêm vào playlist này");
                return new ResponseResult(responseCode);
            }

            SongPlaylist.PlaylistSongId id = new SongPlaylist.PlaylistSongId();

            id.setSongId(dtoRequest.getSongId());
            id.setPlaylistId(dtoRequest.getPlayListId());

            SongPlaylist songPlaylist = new SongPlaylist();
            songPlaylist.setPlaylistSongId(id);

            songPlaylist = songPlaylistRepository.save(songPlaylist);

            return ResponseResult.success(songPlaylist);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }

    }

}

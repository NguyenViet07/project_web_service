package com.example.music.service;

import com.example.music.config.JwtTokenProvider;
import com.example.music.dto.request.SongRequest;
import com.example.music.model.Like;
import com.example.music.model.Song;
import com.example.music.model.SongPlaylist;
import com.example.music.model.User;
import com.example.music.repositories.LikeRepository;
import com.example.music.repositories.SongPlaylistRepository;
import com.example.music.repositories.SongRepository;
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

@Service
@Slf4j
public class SongService {

    @Autowired
    private SongRepository songRepository;

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

    public ResponseResult findBySongId(Long songId, String token) {
        try {

            Song song = songRepository.findAllBySongId(songId);

            SongRequest songRequest = new SongRequest();
            songRequest.setSongName(song.getSongName());
            songRequest.setDescription(song.getDescription());
            songRequest.setUrl(song.getLink());

            songRequest.setLike(0);

            if (token != null) {
                User user = tokenProvider.getUserIdFromJWT(token);
                if (user != null) {
                    Like like = likeRepository.findLikeById(user.getUserId(), songId);
                    if (like != null) {
                        songRequest.setLike(1);
                    }
                }
            }

//            String urlName = "classpath:"+ song.getLink();
//            File data = ResourceUtils.getFile(urlName);
//            Files.readAllBytes(data.toPath());

//            songRequest.setDataSongValue(Files.readAllBytes(data.toPath()));
            return ResponseResult.success(songRequest);
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

    public SongPlaylist addSongPlayList(Long songId, Long playListId) {

        SongPlaylist.PlaylistSongId id = new SongPlaylist.PlaylistSongId();

        id.setSongId(songId);
        id.setPlaylistId(playListId);

        SongPlaylist songPlaylist = new SongPlaylist();
        songPlaylist.setPlaylistSongId(id);

        songPlaylist = songPlaylistRepository.save(songPlaylist);

        return songPlaylist;

    }

}

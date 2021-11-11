package com.example.music.controller;



import com.example.music.config.JwtAuthenticationFilter;
import com.example.music.dto.request.DtoRequest;
import com.example.music.dto.request.SongRequest;
import com.example.music.dto.request.UserRequest;
import com.example.music.model.Song;
import com.example.music.model.User;
import com.example.music.service.SongService;
import com.example.music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/v1/song")
public class SongRestController {

    @Autowired
    private SongService songService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @RequestMapping(path = "singer/create-song", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity createSong(@ModelAttribute SongRequest songRequest, HttpServletRequest request){

        String token = jwtAuthenticationFilter.getJwtFromRequest(request);

        return new ResponseEntity(songService.saveOrUpdate(songRequest, token), HttpStatus.CREATED);
    }

    @PostMapping("/singer/get-list")
    public ResponseEntity getListSongByUserId(@RequestBody SongRequest songRequest, HttpServletRequest request){
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        return new ResponseEntity(songService.getListSongByUserId(songRequest, token), HttpStatus.OK);
    }

    @PostMapping("/info")
    public ResponseEntity findBySongId(@RequestBody SongRequest songRequest, HttpServletRequest request){
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        return new ResponseEntity(songService.findBySongId(songRequest.getSongId(), token), HttpStatus.OK);
    }

    @PostMapping("/singer/add-album")
    public ResponseEntity addSongToAlbum(@RequestBody DtoRequest dtoRequest, HttpServletRequest request){
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        return new ResponseEntity(songService.addSongToAlbum(dtoRequest, token), HttpStatus.OK);
    }

    @PostMapping("/singer/add-playlist")
    public ResponseEntity addSongToPlayList(@RequestBody DtoRequest dtoRequest, HttpServletRequest request){
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        return new ResponseEntity(songService.addSongToPlayList(dtoRequest, token), HttpStatus.OK);
    }


    @PostMapping("/up-view")
    public ResponseEntity upView(@RequestBody SongRequest songRequest){
        return new ResponseEntity(songService.upView(songRequest.getSongId()), HttpStatus.OK);
    }

    @GetMapping("/new-created")
    public ResponseEntity getSongNewCreated(){
        return new ResponseEntity(songService.getSongNewCreated(), HttpStatus.OK);
    }

    @GetMapping("/list-created")
    public ResponseEntity getListSongCreated(){
        return new ResponseEntity(songService.getListSongCreated(), HttpStatus.OK);
    }

    @GetMapping("/list-view")
    public ResponseEntity getListSongView(){
        return new ResponseEntity(songService.getListSongView(), HttpStatus.OK);
    }

    @GetMapping("/download")
    public Object download(@RequestParam("url") String url) {
        File file = new File(url);
        if (file.exists()) {
            try {
                byte[] value = Files.readAllBytes(file.toPath());
                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Disposition", "attachment; filename=" + url.split("/")[url.split("/").length - 1]);
                return ResponseEntity.status(HttpStatus.OK).headers(headers).body(value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "File khong ton tai";
    }

}

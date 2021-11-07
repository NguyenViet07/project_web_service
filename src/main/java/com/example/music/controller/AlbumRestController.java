package com.example.music.controller;



import com.example.music.config.JwtAuthenticationFilter;
import com.example.music.dto.request.SongRequest;
import com.example.music.model.Album;
import com.example.music.service.AlbumService;
import com.example.music.service.SongService;
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
@RequestMapping("/api/v1/album")
public class AlbumRestController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @PostMapping("/singer/create")
    public ResponseEntity createAlbum(@RequestBody Album album, HttpServletRequest request){
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        return new ResponseEntity(albumService.saveOrUpdate(album, token), HttpStatus.CREATED);
    }

}

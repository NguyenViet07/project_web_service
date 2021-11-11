package com.example.music.controller;


import com.example.music.config.JwtAuthenticationFilter;
import com.example.music.dto.request.AlbumRequest;
import com.example.music.dto.request.PlaylistRequest;
import com.example.music.model.Album;
import com.example.music.model.Playlist;
import com.example.music.service.AlbumService;
import com.example.music.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/playlist")
public class PlaylistRestController {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @PostMapping("/get-list")
    public ResponseEntity getListPlaylist(){
        return new ResponseEntity(playlistService.getListPlaylist(), HttpStatus.CREATED);
    }


    @PostMapping("/singer/create")
    public ResponseEntity createPlaylist(@RequestBody Playlist playlist, HttpServletRequest request){
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        return new ResponseEntity(playlistService.saveOrUpdate(playlist, token), HttpStatus.CREATED);
    }

    @PostMapping("/all-user/get-list")
    public ResponseEntity getListMyPlaylist(@RequestBody PlaylistRequest playlistRequest, HttpServletRequest request) {
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        return new ResponseEntity(playlistService.getListMyPlaylist(playlistRequest, token), HttpStatus.OK);
    }

    @PostMapping("/all-user/get-info")
    public ResponseEntity getInfoPlaylist(@RequestBody PlaylistRequest playlistRequest, HttpServletRequest request){
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        return new ResponseEntity(playlistService.getInfoPlaylist(playlistRequest, token), HttpStatus.OK);
    }

    @PostMapping("/all-user/add-song")
    public ResponseEntity addSongToAlbum(@RequestBody PlaylistRequest playlistRequest, HttpServletRequest request){
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        return new ResponseEntity(playlistService.addSongToPlaylist(playlistRequest, token), HttpStatus.OK);
    }

}

package com.example.music.controller;


import com.example.music.config.JwtAuthenticationFilter;
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

    @PostMapping("/singer/create")
    public ResponseEntity createPlaylist(@RequestBody Playlist playlist, HttpServletRequest request){
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        return new ResponseEntity(playlistService.saveOrUpdate(playlist, token), HttpStatus.CREATED);
    }

}

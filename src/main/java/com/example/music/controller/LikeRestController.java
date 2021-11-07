package com.example.music.controller;



import com.example.music.config.JwtAuthenticationFilter;
import com.example.music.dto.request.SongRequest;
import com.example.music.service.LikeService;
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
@RequestMapping("/api/v1/like")
public class LikeRestController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @PostMapping("/all-user/create-song")
    public ResponseEntity createLike(@RequestBody SongRequest songRequest, HttpServletRequest request){
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        return new ResponseEntity(likeService.save(songRequest.getSongId(), token), HttpStatus.CREATED);
    }

    @PostMapping("/all-user/delete-list")
    public ResponseEntity deleteLike(@RequestBody SongRequest songRequest, HttpServletRequest request){
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        return new ResponseEntity(likeService.deleteLike(songRequest.getSongId(), token), HttpStatus.OK);
    }


}

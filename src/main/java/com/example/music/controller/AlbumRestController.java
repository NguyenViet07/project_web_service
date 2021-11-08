package com.example.music.controller;


import com.example.music.config.JwtAuthenticationFilter;
import com.example.music.dto.request.AlbumRequest;
import com.example.music.dto.request.CommentRequest;
import com.example.music.model.Album;
import com.example.music.service.AlbumService;
import com.example.music.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/album")
public class AlbumRestController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

//    @PostMapping("/get-list")
//    public ResponseEntity getListAlbum(@RequestBody AlbumRequest albumRequest){
//        return new ResponseEntity(albumService.getListAlbum(albumRequest), HttpStatus.CREATED);
//    }

    @PostMapping("/all-user/create")
    public ResponseEntity createAlbum(@RequestBody Album album, HttpServletRequest request){
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        return new ResponseEntity(albumService.save(album, token), HttpStatus.CREATED);
    }

    @PostMapping("/all-user/add-song")
    public ResponseEntity addSongToAlbum(@RequestBody AlbumRequest albumRequest, HttpServletRequest request){
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        return new ResponseEntity(albumService.addSongToAlbum(albumRequest, token), HttpStatus.OK);
    }

//    @PostMapping("/all-user/delete")
//    public ResponseEntity deleteComment(@RequestBody CommentRequest commentRequest, HttpServletRequest request){
//        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
//        return new ResponseEntity(albumService.deleteComment(commentRequest.getCommentId(), token), HttpStatus.OK);
//    }

}

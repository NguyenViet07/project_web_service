package com.example.music.controller;


import com.example.music.config.JwtAuthenticationFilter;
import com.example.music.dto.request.CommentRequest;
import com.example.music.dto.request.SongRequest;
import com.example.music.service.CommentService;
import com.example.music.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentRestController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @PostMapping("/get-list-song")
    public ResponseEntity getListComment(@RequestBody CommentRequest commentRequest){
        return new ResponseEntity(commentService.getListCommentBySongId(commentRequest), HttpStatus.CREATED);
    }

    @PostMapping("/all-user/create-song")
    public ResponseEntity createComment(@RequestBody CommentRequest commentRequest, HttpServletRequest request){
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        return new ResponseEntity(commentService.save(commentRequest, token), HttpStatus.CREATED);
    }

    @PostMapping("/all-user/delete")
    public ResponseEntity deleteComment(@RequestBody CommentRequest commentRequest, HttpServletRequest request){
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        return new ResponseEntity(commentService.deleteComment(commentRequest.getCommentId(), token), HttpStatus.OK);
    }


}

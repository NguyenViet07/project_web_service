package com.example.music.controller;



import com.example.music.dto.request.SongRequest;
import com.example.music.model.Song;
import com.example.music.model.User;
import com.example.music.service.SongService;
import com.example.music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/song")
public class SongRestController {

    @Autowired
    private SongService songService;

    @RequestMapping(path = "singer/create", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity createUser(@ModelAttribute SongRequest songRequest, HttpServletRequest request){
        return new ResponseEntity(songService.saveOrUpdate(songRequest), HttpStatus.CREATED);
    }

}

package com.example.music.service;

import com.example.music.repositories.LikeRepository;
import com.example.music.repositories.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;



}

package com.example.music.service;

import com.example.music.repositories.AlbumRepository;
import com.example.music.repositories.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;



}

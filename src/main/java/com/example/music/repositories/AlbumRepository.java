package com.example.music.repositories;

import com.example.music.model.Album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Album findAllByAlbumId(Long albumId);

    List<Album> findAllByUserId(Long userId);
}

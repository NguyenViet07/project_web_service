package com.example.music.repositories;

import com.example.music.model.Album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Album findAllByAlbumId(Long albumId);

}

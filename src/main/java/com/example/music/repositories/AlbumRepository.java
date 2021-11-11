package com.example.music.repositories;

import com.example.music.model.Album;

import com.example.music.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Album findAllByAlbumId(Long albumId);

    List<Album> findAllByUserId(Long userId);

    @Query(value = "SELECT * FROM album a ORDER BY a.created DESC LIMIT 0, 6 ", nativeQuery = true)
    List<Album> getListAlbum();

}
